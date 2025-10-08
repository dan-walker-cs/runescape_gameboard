import { Injectable, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TileModel } from '../../features/game/models/tile.model';
import { TileDialogComponent } from '../../features/game/components/tile-dialog/tile-dialog.component';

@Injectable({ providedIn: 'root' })
export class DialogService {
  private readonly dialog = inject(MatDialog);

  openTileDialog(tile: TileModel) {
    return this.dialog
    .open(TileDialogComponent, { 
            data: tile, 
            panelClass: 'tile-dialog-container' ,
            autoFocus: false
        })
    .afterClosed();
  }
}