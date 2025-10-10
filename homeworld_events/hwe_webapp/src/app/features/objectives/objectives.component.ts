import { Component, inject } from '@angular/core';
import { TileStore } from '../game/data/tile-store.service';
import { NgFor } from '@angular/common';

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

    ngOnInit(): void {
        this.tileStore.init();
    }
}