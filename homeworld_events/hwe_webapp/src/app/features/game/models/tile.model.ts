export interface TileModel {
  id: number;
  title: string;
  desc: string;
  value: number;

  isReserved: boolean;
  reservedBy?: string | null;

  isCompleted: boolean;
  completedBy?: string | null;

  // UI-only state
  isActive?: boolean;
}