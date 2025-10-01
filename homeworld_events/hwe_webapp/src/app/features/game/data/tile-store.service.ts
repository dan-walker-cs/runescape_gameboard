import { Injectable, signal, computed } from '@angular/core';
import { Tile } from '../models/tile.model';

@Injectable({ providedIn: 'root' })
export class TileStore {
    // TODO: Temp; read from backend later

    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _tiles = signal<Tile[]>([
        { id: 1, title: 'Sample Objective', desc: 'Go touch grass', value: 1, isReserved: false, isCompleted: false, isActive: false },
        { id: 2, title: 'Second Objective', desc: 'Drink water', value: 1, isReserved: false, isCompleted: false, isActive: false },
    ]);

    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly tiles = computed(() => this._tiles());

    setActive(id: number, active: boolean) {
        this._mutate(id, t => ({ ...t, isActive: active }));
    }

    toggleReserved(id: number, reservedBy?: string | null) { // Optional parameter allowing null.
        this._mutate(id, t => ({
            ...t,
            isReserved: !t.isReserved,
            reservedBy: !t.isReserved ? (reservedBy ?? t.reservedBy ?? null) : null
            // ??: Nullish Coalescing Operator - use left value if first is non-null, otherwise use right.
        }));
    }

    setReserved(id: number, reservedBy: string | null) {
        this._mutate(id, t => ({ ...t, isReserved: !!reservedBy, reservedBy }));
        // !!: Double Bang Operator - Converts truthy/falsey values into strict boolean true/false.
    }

    setCompleted(id: number, completedBy: string | null) {
        this._mutate(id, t => ({ ...t, isCompleted: !!completedBy, completedBy }));
    }

    updateFromModal(id: number, patch: Partial<Pick<Tile, 'isReserved'|'reservedBy'|'isCompleted'|'completedBy'>>) {
        // Pick<Tile,..>: Creates a new type using field declarations from Tile. New type includes only provided fields.
        // Partial: Wraps Pick to make all fields optional.
        this._mutate(id, t => ({ ...t, ...patch }));
    }

    /**
     * Generic update helper. 
     * Iterates through the tile array & updates the tile with @param id.
     * Updates only the field(s) specified by the provided function @param fn.
     * @param id
     * @param fn
     */
    private _mutate(id: number, fn: (t: Tile) => Tile) {
        this._tiles.update(list => 
            list.map(t => 
                t.id === id ? fn(t): t
            )
        );
    }
}