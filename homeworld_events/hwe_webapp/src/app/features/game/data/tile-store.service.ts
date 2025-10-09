import { Injectable, signal, computed, Signal } from '@angular/core';
import { TileModel } from '../models/tile.model';
import { TileResponse } from './response/tile-response';
import { TileApiService } from './tile-api.service';
import { TileRequest } from './request/tile-request';

@Injectable({ providedIn: 'root' })
export class TileStore {
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _tiles = signal<TileModel[]>([]);

    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly tiles = computed(() => this._tiles());
    
    constructor(private tileApi: TileApiService) {}

    /* TODO: Potentially avoid double-subscriptions & 
        enforce getTilesStreaming() is only called once getTilesBlocking() returns */
    // To be called by dependents in OnInit
    init() {
        this.tileApi.getTilesBlocking().subscribe({
            next: (snapshot) => this._tiles.set(snapshot.map(tileResponse => this._adaptResponseToModel(tileResponse))),
            error: (e) => console.error('[TileStore] snapshot failed', e),
        });

        this.tileApi.getTilesStreaming().subscribe({
            next: (tileResponse) => this._updateLocalState(tileResponse),
            error: (e) => console.error('[TileStore] stream error', e),
        });
    }

    // -- Queries --
    // Fetch a single TileModel
    getTileById(id: number): Signal<TileModel | undefined> {
        return computed(() => this._tiles().find(t => t.id === id));
    }

    // -- Local UI Mutations --
    // Sets the Tile as "selected" in UI; used for CSS styles only
    setSelected(id: number, selected: boolean) {
        this._mutate(id, t => ({ ...t, isSelected: selected }));
    }

    // Completes local mutation, then requests persistent update
    updateFromDialog(id: number, patch: Partial<Pick<TileModel, 'isReserved'|'reservedBy'|'isCompleted'|'completedBy'>>) {
        this._mutate(id, t => ({ ...t, ...patch }));

        const targetTile = this._tiles().find(tile => tile.id ===id);
        if (targetTile) this._updatePersistentState(targetTile);
    }

    // -- Helpers --
    /**
     * Updates UI from Backend
     * @param tileResponse
     */
    private _updateLocalState(tileResponse:TileResponse) {
        let updatedTile = this._adaptResponseToModel(tileResponse);
        updatedTile = this._normalize(updatedTile);

        this._tiles.update( list =>
            list.map(tile => 
                tile.id === updatedTile.id
                    ? updatedTile
                    : tile
            )
        );
    }

    /**
     * Updates backend from UI
     * @param tileModel
     */
    private _updatePersistentState(tileModel: TileModel) {
        let tileUpdateRequest = this._adaptModelToRequest(tileModel);
        this.tileApi.update(tileModel.id, tileUpdateRequest).subscribe({
            next: response => this._updateLocalState(response),
            error: e => console.error('[TileStore] update failed', e)
        });
    }

    /**
     * Updates UI from UI
     * Iterates through the tile array & updates the tile with @param id.
     * Updates only the field(s) specified by the provided function @param fn.
     * @param id
     * @param fn
     */
    private _mutate(id: number, fn: (t: TileModel) => TileModel) {
        this._tiles.update(list => 
            list.map(t => t.id === id ? this._normalize(fn(t)): t)
        );
    }

    /**
     * Applies domain logic to mutated tiles.
     * Domain Logic: Tile Completion data overrides Tile Reservation data.
     * @param mutatedTile 
     * @returns normalizedTile
     */
    private _normalize(mutatedTile: TileModel): TileModel {
        return mutatedTile.isCompleted
            ? { ...mutatedTile, isReserved: false, reservedBy: null }
            : { ...mutatedTile, isCompleted: false, completedBy: null };
    }

    /**
     * Converts backend Response object format to frontend Model object format.
     * @param tileResponse 
     * @returns TileModel
     */
    private _adaptResponseToModel(tileResponse:TileResponse): TileModel {
        return {
            id: tileResponse.id,
            title: tileResponse.title,
            desc: tileResponse.description,
            weight: tileResponse.weight,
            isReserved: tileResponse.isReserved,
            reservedBy: tileResponse.reservedBy,
            isCompleted: tileResponse.isCompleted,
            completedBy: tileResponse.completedBy,
            iconPath: tileResponse.iconPath,
            isSelected: false
        };
    }

    /**
     * Converts frontend Model object format to backend Request object format.
     * @param tileModel
     * @returns TileRequest
     */
    private _adaptModelToRequest(tileModel: TileModel): TileRequest {
        return {
            isReserved: tileModel.isReserved,
            reservedBy: tileModel.reservedBy ?? null,
            isCompleted: tileModel.isCompleted,
            completedBy: tileModel.completedBy ?? null
        };
    }
}