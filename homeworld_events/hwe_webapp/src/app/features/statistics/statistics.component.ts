import { Component, computed, inject, OnInit, signal } from "@angular/core";
import { firstValueFrom, map, startWith, tap } from "rxjs";
import { EventStore } from "../game/data/store/event-store.service";
import { PlayerScoreResponse } from "../game/data/response/player-score-response";
import { PlayerScoreModel } from "../game/models/player-score.model";
import { NgFor } from "@angular/common";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-statistics',
    standalone: true,
    imports: [ NgFor, MatOptionModule, MatSelectModule, MatFormFieldModule, ReactiveFormsModule ],
    templateUrl: './statistics.component.html',
    styleUrls: [ './statistics.component.css' ]
})
export class StatisticsComponent implements OnInit {
    // -- External Data values --
    readonly eventStore = inject(EventStore);
    readonly scores = signal<PlayerScoreModel[]>([]);

    // -- Table Filter values --
    teamFilterCtrl: FormControl<string> = new FormControl<string>('', { nonNullable: true });
    readonly teamFilterValues = computed(() => this.getTeamFilterValues());
    readonly teamFilterSignal = toSignal(
        this.teamFilterCtrl.valueChanges.pipe(startWith(this.teamFilterCtrl.value)),
        { initialValue: this.teamFilterCtrl.value }
    );

    // -- Table Data values --
    readonly totalsByTeam = computed(() => this.getTotalsByTeamDesc());
    readonly totalsByPlayer = computed(() => this.getFilteredTotalsByPlayerDesc(this.teamFilterSignal()));


    async ngOnInit(): Promise<void> {
        await firstValueFrom(this.eventStore.init());

        this.eventStore.getPlayerTeamScores().pipe(
            map(playerScoreResponseList => playerScoreResponseList
                .map(playerScoreResponse => this._adaptResponseToModel(playerScoreResponse))),
            tap(playerScoreModelList => this.scores.set(playerScoreModelList))
        ).subscribe();
    }

    /**
     * Provides Team name data for the table filter.
     * @returns string[]
     */
    private getTeamFilterValues(): string[] {
        const teamNames: string[] = this.scores()
            .map(playerScoreModel => playerScoreModel.teamName);
        
        return [...new Set(teamNames)];
    }

    /**
     * Calculates aggregate Players' score by Team & returns results in DESC order.
     * @returns 
     */
    private getTotalsByTeamDesc(): { teamName: string; total: number }[] {
        const scoreByTeam = new Map<string, number>();
        for (const model of this.scores()) {
            scoreByTeam.set(model.teamName, (scoreByTeam.get(model.teamName) ?? 0) + model.score);
        }

        return Array
            .from(scoreByTeam, ([teamName, total]) => ({teamName, total}))
            .sort((a, b) => b.total - a.total);
    }

    /**
     * Returns local scores signal results by Player score DESC.
     * @returns PlayerScoreModel[]
     */
    private getFilteredTotalsByPlayerDesc(teamFilterValue: string): PlayerScoreModel[] {
        return [...this.scores()]
            .sort((a, b) => b.score - a.score)
            .filter(playerScoreModel => this.doTeamFilter(playerScoreModel, teamFilterValue));
    }
    
    /**
     * Returns whether the given PlayerScoreModel's teamName value appears in the teamFilterCtrl.
     * @param playerScoreModel 
     * @returns boolean
     */
    private doTeamFilter(playerScoreModel: PlayerScoreModel, teamFilterValue: string): boolean {
        return teamFilterValue === playerScoreModel.teamName || teamFilterValue === '';
    }

    /**
     * Converts a backend response to associated frontend model.
     * @param playerScoreResponse
     * @returns PlayerScoreModel
     */
    private _adaptResponseToModel(playerScoreResponse: PlayerScoreResponse): PlayerScoreModel {
        return {
            teamId: playerScoreResponse.teamId,
            teamName: playerScoreResponse.teamName,
            playerId: playerScoreResponse.playerId,
            playerName: playerScoreResponse.playerName,
            score: playerScoreResponse.score
        };
    }
}
