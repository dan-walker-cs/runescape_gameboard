import { GridTile } from "./grid-tile";

/**
 * Frontend Data Store Board model-object.
 */
export interface BoardModel {
    id: number;
    widthQ: number;
    heightR: number;
    gridTileList: GridTile[];
}