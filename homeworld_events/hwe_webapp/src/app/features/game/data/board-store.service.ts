import { computed, inject, Injectable, signal } from "@angular/core";
import { EventStore } from "./event-store.service";
import { BoardModel } from "../models/board.model";
import { BoardResponse } from "./response/board-response";
import { BoardApiService } from "./board-api.service";
import { BoardTileResponse } from "./response/board-tile-response";
import { map, Observable, shareReplay, switchMap, take, tap } from "rxjs";

/**
 *  Central location to retrieve stateful Tile data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class BoardStore {
    readonly eventStore = inject(EventStore);
    
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _board = signal<BoardModel | null>(null);
    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly board = computed(() => this._board());

    // Memo
    private init$?: Observable<BoardModel>

    constructor(private boardApi: BoardApiService) {}

    // To be called by dependents in OnInit
    init(): Observable<BoardModel> {
        // Subscribe once, then cache
        if (!this.init$) {
            // Wait for EventStore to initialize
            this.init$ = this.eventStore.init()
                .pipe(
                    // Load the Board
                    switchMap(eventModel => this.boardApi.getEventBoardSnapshot(eventModel.id)),
                    map(boardResponse => this._adaptBoardResponseToModel(boardResponse)),
                    tap(boardModel => this._board.set(boardModel)),

                    // Load GridTiles
                    switchMap(boardModel =>
                        this.boardApi.getGridTilesByBoard(boardModel.id)
                            .pipe(
                                tap(boardTileResponse => this.setGridTileList(boardTileResponse)),
                                map(() => boardModel)
                            )
                    ),
                    take(1),
                    shareReplay(1)
                )
        }

        return this.init$;
    }

    /**
     * Converts backend BoardResponse object format to frontend BoardModel object format.
     * @param boardResponse 
     * @returns BoardModel
     */
    private _adaptBoardResponseToModel(boardResponse: BoardResponse): BoardModel {
        return {
            id: boardResponse.id,
            widthQ: boardResponse.widthQ,
            heightR: boardResponse.heightR,
            gridTileList: []
        };
    }

    // -- Local UI Mutations --
    /**
     * Updates the local Board with a list of Grid Tiles - usually a 1-time operation 
     *  from the backend to build the Board's grid.
     * @param boardTileResponse 
     */
    setGridTileList(boardTileResponse: BoardTileResponse): void {
        this._mutate(b => ({ ...b, gridTileList: boardTileResponse.gridTileList }));
    }

    /**
     * Updates UI from UI
     * Updates Board given id @param id.
     * Updates only the field(s) specified by the provided function @param fn.
     * @param id
     * @param fn
     */
    private _mutate(fn: (b: BoardModel) => BoardModel): void {
        this._board.update(oldB => (oldB ? fn(oldB) : oldB));
    }
}