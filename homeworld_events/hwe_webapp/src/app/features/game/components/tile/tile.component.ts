import { Component, computed, HostBinding, inject, Input, Signal } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { TileModel } from '../../models/tile.model';
import { TileStore } from '../../data/tile-store.service';
import { DialogService } from '../../../../core/services/dialog.service';


@Component({
  selector: 'app-tile',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  templateUrl: './tile.component.html',
  styleUrl: './tile.component.css'
})
export class TileComponent {
  private readonly tileStore = inject(TileStore);
  private readonly tileDialogs = inject(DialogService);

  // Retrieve tile based on id input
  @Input({ required: true }) tileId!: number;
  tile: Signal<TileModel | undefined> = computed(() => this.tileStore.getTileById(this.tileId)());

  // Retrieve tile fields required for binding
  @HostBinding('class.select') get isSelected() { return !!this.tile()?.isSelected };
  @HostBinding('class.complete') get isCompleted() { return !!this.tile()?.isCompleted };
  @HostBinding('class.reserve') get isReserved() { return !!this.tile()?.isReserved };

  onTileSelect() {
    // Create a clean snapshot of the current value & activate if exists
    const t = this.tile();
    if (!t) return;
    this.tileStore.setSelected(t.id, true);

    // Subscribe to dialog changes & update the store to reflect
    this.tileDialogs.openTileDialog(t).subscribe((result?: Partial<TileModel>) => {
      if (result)
        this.tileStore.updateFromDialog(t.id, {
          isReserved: result.isReserved,
          reservedBy: result.reservedBy ?? null,
          isCompleted: result.isCompleted,
          completedBy: result.completedBy ?? null
        });

      this.tileStore.setSelected(t.id, false);
    });
  }
}
