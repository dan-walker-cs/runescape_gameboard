import { Component, inject } from '@angular/core';
import { NgFor } from '@angular/common';
import { firstValueFrom } from 'rxjs';
import { TileStore } from '../game/data/store/tile-store.service';
import { TileModel } from '../game/models/tile.model';

@Component({
    selector: 'app-objectives',
    standalone: true,
    imports: [ NgFor ],
    templateUrl: './objectives.component.html',
    styleUrls: ['./objectives.component.css']
})
export class ObjectivesComponent {
    // Dynamic immutable Tile data from the backend
    readonly tileStore = inject(TileStore);

    async ngOnInit(): Promise<void> {
        await firstValueFrom(this.tileStore.init());
    }

    /**
     * Filters out non-Objective Tiles for the table display.
     * @returns TileModel[]
     */
    getObjectiveTiles(): TileModel[] {
        return this.tileStore.tiles()
            .filter(tileModel => tileModel.weight > 0);
    }
}