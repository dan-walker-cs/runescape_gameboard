import { computed, Injectable, signal } from "@angular/core";
import { PlayerApiService } from "./player-api.service";
import { PlayerResponse } from "./response/player-response";
import { PlayerModel } from "../models/player.model";

/**
 *  Central location to retrieve stateful Player data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class PlayerStore {
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _players = signal<PlayerModel[]>([]);
    
    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly players = computed(() => this._players());
    
    constructor(private playerApi: PlayerApiService) {}

    // To be called by dependents in OnInit
    init(): void {
        this.playerApi.getPlayersSnapshot().subscribe({
            next: (responseList) => this._players.set(responseList.map(playerResponse => this._adaptResponseToModel(playerResponse))),
            error: (e) => console.error('[PlayerStore] snapshot failed', e),
        });
    }

    /**
     * Converts backend Response object format to frontend Model object format.
     * @param playerResponse 
     * @returns PlayerModel
     */
    private _adaptResponseToModel(playerResponse: PlayerResponse): PlayerModel {
        return {
            id: playerResponse.id,
            displayName: playerResponse.displayName
        };
    }
}