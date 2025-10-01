import { Injectable, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Tile } from '../../features/game/models/tile.model';
import { TileModal } from '../../features/game/components/tile-modal/tile-modal.component';

@Injectable({ providedIn: 'root' })
export class ModalService {
  private readonly dialog = inject(MatDialog);

  openTileModal(tile: Tile) {
    return this.dialog
    .open(TileModal, { 
        data: tile, 
        panelClass: 'tile-modal-container' }
    )
    .afterClosed();
  }
}