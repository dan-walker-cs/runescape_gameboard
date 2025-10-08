import { Injectable, signal, computed, Signal } from '@angular/core';
import { TileModel } from '../models/tile.model';
import { TileResponse } from './response/tile-response';
import { TileApiService } from './tile-api.service';

@Injectable({ providedIn: 'root' })
export class TileStore {
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _tiles = signal<TileModel[]>([]);

    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly tiles = computed(() => this._tiles());
    
    constructor(private tileApi: TileApiService) {}

    // To be called by dependents in OnInit
    init() {
        this.tileApi.getTilesBlocking().subscribe({
            next: (snapshot) => this._tiles.set(snapshot.map(tileResponse => this._adaptResponseToModel(tileResponse))),
            error: (e) => console.error('[TileStore] snapshot failed', e),
        });

        this.tileApi.getTilesStreaming().subscribe({
            next: (tileResponse) => this._update(tileResponse),
            error: (e) => console.error('[TileStore] stream error', e),
        });
    }

    // -- Queries --
    // Fetch a single TileModel
    getTileById(id: number): Signal<TileModel | undefined> {
        return computed(() => this._tiles().find(t => t.id === id));
    }

    // -- Local UI Mutations --
    // Sets the Tile as active in UI; used for CSS styles only
    setActive(id: number, active: boolean) {
        this._mutate(id, t => ({ ...t, isActive: active }));
    }

    // Updates a Pick of Tile fields from the TileDialog
    updateFromDialog(id: number, patch: Partial<Pick<TileModel, 'isReserved'|'reservedBy'|'isCompleted'|'completedBy'>>) {
        // Pick<Tile,..>: Creates a new type using field declarations from Tile. New type includes only provided fields.
        // Partial: Wraps Pick to make all fields optional.
        this._mutate(id, t => ({ ...t, ...patch }));
    }

    // -- Helpers --
    /**
     * Updates the private signal in-palce with incoming TileResponse data.
     * @param tileResponse 
     */
    private _update(tileResponse:TileResponse) {
        const updatedTile = this._adaptResponseToModel(tileResponse);

        this._tiles.update( list =>
            list.map(tile => 
                tile.id === updatedTile.id
                    ? this._normalize(updatedTile)
                    : tile
            )
        );
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
            value: tileResponse.weight,
            isReserved: tileResponse.isReserved,
            reservedBy: tileResponse.reservedBy,
            isCompleted: tileResponse.isCompleted,
            completedBy: tileResponse.completedBy,
            iconPath: tileResponse.iconPath,
            isActive: false
        };
    }

    /**
     * Generic update helper. 
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
            : mutatedTile;
    }
}