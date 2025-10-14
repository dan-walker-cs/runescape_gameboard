/**
 * Frontend Data Store Tile model-object.
 */
export interface TileModel {
    relId: number;
    teamId: number;
    teamName: string;
    tileId: number;
    title: string;
    desc: string;
    weight: number;
    isReserved: boolean;
    reservedBy?: string | null;
    isCompleted: boolean;
    completedBy?: string | null;
    iconPath: string;
    
    // UI-only state
    isSelected?: boolean;
}