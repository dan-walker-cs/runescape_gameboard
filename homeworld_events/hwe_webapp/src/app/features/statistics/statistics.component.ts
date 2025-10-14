import { Component, computed, inject, OnInit, signal } from "@angular/core";
import { firstValueFrom, map, tap } from "rxjs";
import { EventStore } from "../game/data/store/event-store.service";
import { PlayerScoreResponse } from "../game/data/response/player-score-response";
import { PlayerScoreModel } from "../game/models/player-score.model";
import { NgFor } from "@angular/common";

@Component({
    selector: 'app-statistics',
    standalone: true,
    imports: [ NgFor ],
    templateUrl: './statistics.component.html',
    styleUrls: [ './statistics.component.css' ]
})
export class StatisticsComponent implements OnInit {
    readonly eventStore = inject(EventStore);

    readonly scores = signal<PlayerScoreModel[]>([]);

    
    readonly totalsByTeam = computed(() => this.getTotalsByTeamDesc());
    readonly totalsByPlayer = computed(() => this.getTotalsByPlayerDesc());

    async ngOnInit(): Promise<void> {
        await firstValueFrom(this.eventStore.init());

        this.eventStore.getPlayerTeamScores().pipe(
            map(playerScoreResponseList => playerScoreResponseList
                .map(playerScoreResponse => this._adaptResponseToModel(playerScoreResponse))),
            tap(playerScoreModelList => this.scores.set(playerScoreModelList))
        ).subscribe();
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
    private getTotalsByPlayerDesc(): PlayerScoreModel[] {
        return [...this.scores()]
            .sort((a, b) => b.score - a.score);
    }
}
