/**
 * Tile Request object for backend PATCH/UPDATE requests.
 */
export interface TileRequest {
  isReserved: boolean;
  reservedBy: string | null;
  isCompleted: boolean;
  completedBy: string | null;
}