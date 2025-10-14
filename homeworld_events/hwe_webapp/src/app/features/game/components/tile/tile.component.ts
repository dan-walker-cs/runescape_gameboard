import { Component, computed, HostBinding, inject, Input, Signal } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { TileModel } from '../../models/tile.model';
import { DialogService } from '../../../../core/services/dialog.service';
import { L1_WARP_TILE_IDS, L2_WARP_TILE_IDS, L3_WARP_TILE_IDS } from './tile.constants';
import { TileStore } from '../../data/store/tile-store.service';

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
    @Input() tileData: TileModel | null = null;
    tile: Signal<TileModel | undefined> = computed(() => this.tileStore.getTileById(this.tileId)());

    // Retrieve tile fields required for binding
    @HostBinding('class.select') get isSelected() { return !!this.tile()?.isSelected };
    @HostBinding('class.complete') get isCompleted() { return !!this.tile()?.isCompleted };
    @HostBinding('class.reserve') get isReserved() { return !!this.tile()?.isReserved };

    onSelectTile() {
        // Create a clean snapshot of the current value & select if exists
        const t = this.tile();
        if (!t) return;
        this.tileStore.setSelected(t.tileId, true);

        // Subscribe to dialog changes & update the store to reflect
        this.tileDialogs.openTileDialog(t).subscribe((result?: Partial<TileModel>) => {
            if (result)
                this.tileStore.updateFromDialog(t.tileId, {
                isReserved: result.isReserved,
                reservedBy: result.reservedBy ?? null,
                isCompleted: result.isCompleted,
                completedBy: result.completedBy ?? null
                });

            // Unselect Tile on dialog closure
            this.tileStore.setSelected(t.tileId, false);
        });
    }

    // Returns whether a Tile is assocaited with an Objective - currently used to disable checkboxes.
    isNonObjectiveTile(tileModel: TileModel): boolean {
        return tileModel.weight === 0;
    } // TODO: this goes in tile-dialog

    getVariableTileStyles(tileModel?: TileModel): Record<string, boolean> {
        return { ...this.warpClasses(tileModel?.tileId), ...this.difficultyClasses(tileModel?.weight) };
    }

    // Defines Warp Tile style classes
    private warpClasses(id?: number) {
        const lvl = this.determineWarpLevel(id);
        return {
            'warp--l1': lvl === 1,
            'warp--l2': lvl === 2,
            'warp--l3': lvl === 3,
        };
    }

    // Defines Objective Tile style classes based on difficulty / point value
    private difficultyClasses(weight?: number) {
        const lvl = this.determineDifficultyLevel(weight);
        return {
            'diff--none': lvl === 0,
            'diff--easy': lvl === 1,
            'diff--med': lvl === 2,
            'diff--hard': lvl === 3,
        };
    }

    // Determines whether a Tile needs additional "Warp" styling
    private determineDifficultyLevel(weight?: number): 0|1|2|3 {
        if (!weight) return 0;
        if (weight === 3) return 3;
        if (weight === 2) return 2;
        if (weight === 1) return 1;
        return 0;
    }

    // Determines whether a Tile needs additional "Warp" styling
    private determineWarpLevel(id?: number): 0|1|2|3 {
        if (!id) return 0;
        if (L3_WARP_TILE_IDS.has(id)) return 3;
        if (L2_WARP_TILE_IDS.has(id)) return 2;
        if (L1_WARP_TILE_IDS.has(id)) return 1;
        return 0;
    }
}
