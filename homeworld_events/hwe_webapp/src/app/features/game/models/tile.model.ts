export interface TileModel {
  id: number;
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