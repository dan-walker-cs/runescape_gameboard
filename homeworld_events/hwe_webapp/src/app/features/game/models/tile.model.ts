export interface TileModel {
  id: number;
  title: string;
  desc: string;
  value: number;
  isReserved: boolean;
  reservedBy?: string | null;
  isCompleted: boolean;
  completedBy?: string | null;
  iconPath: string;
  
  // UI-only state
  isActive?: boolean;
}