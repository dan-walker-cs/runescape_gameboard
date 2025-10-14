import { computed, inject, Injectable, signal } from "@angular/core";
import { map, Observable, shareReplay, switchMap, take, tap } from "rxjs";
import { TeamApiService } from "../api/team-api.service";
import { EventStore } from "./event-store.service";
import { TeamPlayerResponse } from "../response/team-player-response";
import { TeamModel } from "../../models/team.model";

/**
 *  Central location to retrieve stateful Team data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class TeamStore {
    private eventStore = inject(EventStore);
    private playerApi = inject(TeamApiService);

    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _teams = signal<TeamModel[]>([]);
    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly teams = computed(() => this._teams());

    // Memo
    private init$?: Observable<TeamModel[]>;


    // To be called by dependents in OnInit
    init(): Observable<TeamModel[]> {
        if (!this.init$) {
            this.init$ = this.eventStore.init()
                .pipe(
                    switchMap(eventModel => this.playerApi.getPlayerTeamsSnapshotByEvent(eventModel.id)),
                    map(teamPlayerResponseList => teamPlayerResponseList.map(teamPlayerResponse =>
                            this._adaptResponseToModel(teamPlayerResponse))
                    ),
                    tap(teamPlayerModelList => this._teams.set(teamPlayerModelList)),
                    take(1),
                    shareReplay(1)
                )
        }

        return this.init$;
    }

    /**
     * Converts backend Response object format to frontend Model object format.
     * @param teamPlayerResponse 
     * @returns TeamModel
     */
    private _adaptResponseToModel(teamPlayerResponse: TeamPlayerResponse): TeamModel {
        return {
            id: teamPlayerResponse.teamId,
            name: teamPlayerResponse.teamName,
            playerNames: teamPlayerResponse.playerNames
        };
    }
}