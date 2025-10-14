/**
 * Tile Request object for backend PATCH/UPDATE requests.
 */
export interface TileUpdateRequest {
    eventId: number,
    isReserved: boolean;
    reservedBy: string | null;
    isCompleted: boolean;
    completedBy: string | null;
}