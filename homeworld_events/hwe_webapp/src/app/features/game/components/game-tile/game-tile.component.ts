import { Component, HostBinding } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { TileModal } from '../tile-modal/tile-modal.component';
import { Tile } from '../../models/tile.model';


@Component({
  selector: 'app-game-tile',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  templateUrl: './game-tile.component.html',
  styleUrl: './game-tile.component.css'
})
export class GameTile {
  @HostBinding('class.active') isActive: boolean = false;
  // TODO: replace with backend persisted state once SB setup complete
  @HostBinding('class.complete') isCompleted: boolean = false;
  // TODO: replace with backend persisted state once SB setup complete
  @HostBinding('class.reserve') isReserved: boolean = false;

  // TODO: replace with backend persisted state once SB setup complete
  reservedBy: string = '';
  // TODO: replace with backend persisted state once SB setup complete
  completedBy: string = '';

  constructor(public tileModal: MatDialog) {}

  onTileSelect() {
    this.isActive = true;
    // TODO: Move to a declaration file & ensure isComplete has a default false value
    const tile: Tile = {
      id: 1,
      title: 'Sample Objective',
      desc: 'Go touch grass',
      value: 1,
      isReserved: this.isReserved,
      reservedBy: this.reservedBy,
      isCompleted: this.isCompleted,
      completedBy: this.completedBy
    };

    this.tileModal
      .open(TileModal, { data: tile, panelClass: 'tile-modal-container' })
      .afterClosed()
      .subscribe((result?: { isReserved: boolean; reservedBy: string; isCompleted: boolean; completedBy: string }) => {
        // TODO: Refactor
        if (result) {
          if (result.isCompleted) {
            this.isReserved = false;
            this.reservedBy = '';
          } else {
            this.isReserved = result.isReserved;
            this.reservedBy = this.isReserved ? result.reservedBy : '';
          }

          this.isCompleted = result.isCompleted;
          this.completedBy = this.isCompleted ? result.completedBy : '';
        }
        this.isActive = false;
      });
    }
}
