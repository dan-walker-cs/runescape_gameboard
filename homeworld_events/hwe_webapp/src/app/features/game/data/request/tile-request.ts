export interface TileRequest {
  isReserved: boolean;
  reservedBy: string | null;
  isCompleted: boolean;
  completedBy: string | null;
}