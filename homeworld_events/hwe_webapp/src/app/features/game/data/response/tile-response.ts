/**
 * Tile Response object for backend GET requests.
 */
export interface TileResponse {
  id: number;
  title: string;
  description: string;
  weight: number;
  isReserved: boolean;
  reservedBy: string | null;
  isCompleted: boolean;
  completedBy: string | null;
  iconPath: string;
}