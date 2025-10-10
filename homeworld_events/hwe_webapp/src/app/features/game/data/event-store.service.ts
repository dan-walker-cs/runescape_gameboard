import { computed, Injectable, signal } from "@angular/core";
import { EventModel } from "../models/event.model";
import { EventResponse } from "./response/event-response";
import { EventApiService } from "./event-api.service";
import { EventPlayerTeamResponse } from "./response/event-player-team-response";
import { TeamModel } from "../models/team.model";
import { HIGHLANDER_NUM } from "../../../shared/constant/common-constant";

/**
 *  Central location to retrieve stateful Event data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class EventStore {
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _event = signal<EventModel | null>(null);
    private _teams = signal<TeamModel[]>([]);

    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly event = computed(() => this._event());  
    readonly teams = computed(() => this._teams());

    constructor(private eventApi: EventApiService) {}

    // To be called by dependents in OnInit
    init(): void {
        this.eventApi.getCurrentEventSnapshot().subscribe({
            next: (eventResponse) => this._event.set(this._adaptEventResponseToModel(eventResponse)),
            error: (e) => console.error('[EventStore] snapshot failed', e),
        });

        this.eventApi.getPlayerAndTeamSnapshot(this._event()?.id ?? HIGHLANDER_NUM).subscribe({
            next: (eptResponse) => this._teams.set(this._adaptTeamResponseToModel(eptResponse)),
            error: (e) => console.error('[EventStore] snapshot failed', e),
        });
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