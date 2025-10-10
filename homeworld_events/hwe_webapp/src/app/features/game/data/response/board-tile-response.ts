import { GridTile } from "../../models/grid-tile";

/**
 * BoardTile Response object for backend GET requests.
 */
export interface BoardTileResponse {
    boardId: number;
    gridTileList: GridTile[];
}