import { Component, computed, HostBinding, inject, Input, Signal } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { Tile } from '../../models/tile.model';
import { TileStore } from '../../data/tile-store.service';
import { ModalService } from '../../../../core/services/modal.service';


@Component({
  selector: 'app-game-tile',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  templateUrl: './game-tile.component.html',
  styleUrl: './game-tile.component.css'
})
export class GameTile {
  private readonly tileStore = inject(TileStore);
  private readonly tileModals = inject(ModalService);

  // Retrieve tile based on id input
  @Input({ required: true }) tileId!: number;
  tile: Signal<Tile | undefined> = computed(() => this.tileStore.getTileById(this.tileId)());

  // Retrieve tile fields required for binding
  @HostBinding('class.active') get isActive() { return !!this.tile()?.isActive };
  @HostBinding('class.complete') get isCompleted() { return !!this.tile()?.isCompleted };
  @HostBinding('class.reserve') get isReserved() { return !!this.tile()?.isReserved };

  onTileSelect() {
    // Create a clean snapshot of the current value & activate if exists
    const t = this.tile();
    if (!t) return;
    this.tileStore.setActive(t.id, true);

    // Subscribe to modal changes & update the store to reflect
    this.tileModals.openTileModal(t).subscribe((result?: Partial<Tile>) => {
      if (result)
        this.tileStore.updateFromModal(t.id, {
          isReserved: result.isReserved,
          reservedBy: result.reservedBy ?? null,
          isCompleted: result.isCompleted,
          completedBy: result.completedBy ?? null
        });

      this.tileStore.setActive(t.id, false);
    });
  }
}
