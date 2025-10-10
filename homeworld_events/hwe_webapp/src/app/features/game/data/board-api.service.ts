import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { BoardResponse } from "./response/board-response";
import { endpoints } from "../../../shared/api/endpoints";
import { BoardTileResponse } from "./response/board-tile-response";

@Injectable({ providedIn: 'root' })
export class BoardApiService {

    private http = inject(HttpClient);

    // -- Readings Endpoints --
    // Retrieve initial state
    getEventBoardSnapshot(eventId: number): Observable<BoardResponse> {
        return this.http.get<BoardResponse>(endpoints.boards.snapshot(eventId));
    }
    // Retieves Tile-Coordinate relationships for the given Board.
    getGridTilesByBoard(boardId: number): Observable<BoardTileResponse> {
        return this.http.get<BoardTileResponse>(endpoints.boards.gridTilesByBoard(boardId));
    }
}