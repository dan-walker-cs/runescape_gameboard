import { computed, inject, Injectable, signal } from "@angular/core";
import { EventModel } from "../models/event.model";
import { EventResponse } from "./response/event-response";
import { EventApiService } from "./event-api.service";
import { EventPlayerTeamResponse } from "./response/event-player-team-response";
import { TeamModel } from "../models/team.model";
import { map, Observable, shareReplay, switchMap, take, tap } from "rxjs";

/**
 *  Central location to retrieve stateful Event data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class EventStore {
    private eventApi = inject(EventApiService);
    
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _event = signal<EventModel | null>(null);
    private _teams = signal<TeamModel[]>([]);

    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly event = computed(() => this._event());  
    readonly teams = computed(() => this._teams());

    // Memo
    private init$?: Observable<EventModel>;


    // To be called by dependents in OnInit
    init(): Observable<EventModel> {
        if (!this.init$) {
            this.init$ = this.eventApi.getCurrentEventSnapshot()
                .pipe(
                    map(eventResponse => this._adaptEventResponseToModel(eventResponse)),
                    tap(eventModel => this._event.set(eventModel)),

                    switchMap(eventModel =>
                        this.eventApi.getPlayerAndTeamSnapshot(eventModel.id)
                            .pipe(
                                map(eptResponse => this._adaptTeamResponseToModel(eptResponse)),
                                tap(teamModelList => this._teams.set(teamModelList)),
                                map(() => eventModel)
                            )
                    ),
                    take(1),
                    shareReplay(1)
                );
            }

        return this.init$;
    }

    /**
     * Converts backend EventResponse object format to frontend EventModel object format.
     * @param eventResponse 
     * @returns EventModel
     */
    private _adaptEventResponseToModel(eventResponse: EventResponse): EventModel {
        return {
            id: eventResponse.id,
            title: eventResponse.title,
            startDt: eventResponse.startDt,
            endDt: eventResponse.endDt,
            buyIn: eventResponse.buyIn,
            rulesPath: eventResponse.rulesPath
        };
    }

    /**
     * Converts backend EventPlayerTeamResponse object format to a list of TeamModel objects.
     * @param eptResponse 
     * @returns TeamModel[]
     */
    private _adaptTeamResponseToModel(eptResponse: EventPlayerTeamResponse): TeamModel[] {
        return Object.entries(eptResponse.playerNamesByTeam)
            .map(([teamName, players]) => ({
                teamName,
                players: [...players],
            }));
    }
}