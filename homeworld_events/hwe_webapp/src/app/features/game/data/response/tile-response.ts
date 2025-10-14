/**
 * Tile Response object for backend GET requests.
 */
export interface TileResponse {
    relId: number;
    teamId: number;         // TODO: potential cut
    teamName: string;       // TODO: potential cut
    tileId: number;         // TODO: potential cut
    title: string;
    description: string;
    weight: number;
    isReserved: boolean;
    reservedBy: string | null;
    isCompleted: boolean;
    completedBy: string | null;
    iconPath: string;
}