import { Injectable, signal, computed, Signal, inject, Injector, DestroyRef } from '@angular/core';
import { catchError, EMPTY, filter, map, Observable, shareReplay, Subscription, switchMap, take, tap } from 'rxjs';
import { TileApiService } from '../api/tile-api.service';
import { TileModel } from '../../models/tile.model';
import { TeamStore } from './team-store.service';
import { TileResponse } from '../response/tile-response';
import { TileUpdateRequest } from '../request/tile-update-request';
import { takeUntilDestroyed, toObservable } from '@angular/core/rxjs-interop';

/**
 *  Central location to retrieve stateful Tile data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class TileStore {
    private tileApi = inject(TileApiService);
    private teamStore = inject(TeamStore);

    private injector   = inject(Injector);
    private destroyRef = inject(DestroyRef);

    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _tiles = signal<TileModel[]>([]);
    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly tiles = computed(() => this._tiles());

    // Currently selected team (controls snapshot + stream)
    private selectedTeamId = signal<number | null>(null);

    // Memo
    private snapshotInit$?: Observable<TileModel[]>;
    private streamInit$?: Subscription;


    // To be called by dependents in OnInit
    init(): Observable<TileModel[]> {
        if (!this.snapshotInit$) {
            this.setDefaultSelectedTeam();
            const selectedTeamId = this.convertSelectedTeamToObservable();

            // Get snapshot state
            this.snapshotInit$ = selectedTeamId.pipe(
                tap(() => this._tiles.set([])), // hot-reload visual clear; check this is necessary
                switchMap(teamId => this.tileApi.getTilesSnapshotByTeam(teamId)),
                map(tileResponseList => tileResponseList.map(tileResponse => this._adaptResponseToModel(tileResponse))),
                tap(tileModelList => this._tiles.set(tileModelList)),
                shareReplay(1)
            );

            // Get updates streaming
            this.streamInit$ = selectedTeamId.pipe(
                switchMap(teamId => this.tileApi.getTilesStreamingByTeam(teamId).pipe(
                    tap(tileResponse => this._updateLocalState(tileResponse)),
                    catchError(e => { console.error('[TileStore] stream error', e); return EMPTY; }),
                    takeUntilDestroyed(this.destroyRef)
                ))
            ).subscribe();
        }

        return this.snapshotInit$;
    }

    // API Toggle by Team data
    selectTeam(teamId: number): void {
        if (this.selectedTeamId() === teamId) return;
        this.selectedTeamId.set(teamId);
    }

    // API execute by Team data - snapshot
    loadSnapshotByTeam(teamId: number): Observable<TileModel[]> {
        return this.tileApi.getTilesSnapshotByTeam(teamId).pipe(
            map(tileResponseList => tileResponseList.map(tileResponse => this._adaptResponseToModel(tileResponse))),
                tap(tileModelList => this._tiles.set(tileModelList))
        );
    }

    // API execute by Team data - stream
    startStreamByTeam(teamId: number): void {
        this.streamInit$?.unsubscribe();
        this.streamInit$ = this.tileApi.getTilesStreamingByTeam(teamId).pipe(
            tap(tileResponse => this._updateLocalState(tileResponse)),
            catchError(e => { console.error('[TileStore] stream error', e); return EMPTY; }),
            takeUntilDestroyed(this.destroyRef)
        )
        .subscribe();
    }



    // -- Queries --
    // Fetch a single TileModel
    getTileById(tileId: number): Signal<TileModel | undefined> {
        return computed(() => this._tiles().find(t => t.tileId === tileId));
    }

    // -- Local UI Mutations --
    // Sets the Tile as "selected" in UI; used for CSS styles only
    setSelected(tileId: number, selected: boolean) {
        this._mutate(tileId, t => ({ ...t, isSelected: selected }));
    }

    // Completes local mutation, then requests persistent update
    updateFromDialog(tileId: number, patch: Partial<Pick<TileModel, 'isReserved'|'reservedBy'|'isCompleted'|'completedBy'>>) {
        this._mutate(tileId, t => ({ ...t, ...patch }));

        const targetTile = this._tiles().find(tile => tile.tileId === tileId);
        if (targetTile) this._updatePersistentState(targetTile);
    }

    // -- Helpers --
    /**
     * Converts the default TeamStore state result to an Observable for downstream operations.
     * @returns Observable<number>
     */
    private convertSelectedTeamToObservable(): Observable<number> {
        return toObservable(this.selectedTeamId, { injector: this.injector })
            .pipe(
                filter((teamId): teamId is number => teamId != null)
            );
    }

    /**
     * Waits for the TeamStore to initialize, then takes the first result to populate the default State.
     */
    private setDefaultSelectedTeam(): void {
        this.teamStore.init().pipe(
            take(1)
        ).subscribe(teams => {
            if (this.selectedTeamId() == null && teams.length) {
                this.selectTeam(teams[0].id);
            }
        });
    }

    /**
     * Updates UI from Backend
     * @param tileResponse
     */
    private _updateLocalState(tileResponse: TileResponse) {
        let updatedTile = this._adaptResponseToModel(tileResponse);
        updatedTile = this._normalize(updatedTile);

        this._tiles.update( list =>
            list.map(tile => 
                tile.tileId === updatedTile.tileId
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
        this.tileApi.update(tileModel.relId, tileUpdateRequest).subscribe({
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
    private _mutate(tileId: number, fn: (t: TileModel) => TileModel) {
        this._tiles.update(list => 
            list.map(oldT => oldT.tileId === tileId ? this._normalize(fn(oldT)): oldT)
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
    private _adaptResponseToModel(tileResponse: TileResponse): TileModel {
        return {
            relId: tileResponse.relId,
            teamId: tileResponse.teamId,
            teamName: tileResponse.teamName,
            tileId: tileResponse.tileId,
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
    private _adaptModelToRequest(tileModel: TileModel): TileUpdateRequest {
        return {
            isReserved: tileModel.isReserved,
            reservedBy: tileModel.reservedBy ?? null,
            isCompleted: tileModel.isCompleted,
            completedBy: tileModel.completedBy ?? null
        };
    }
}