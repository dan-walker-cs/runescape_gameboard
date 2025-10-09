import { Component, inject, OnInit, Signal } from '@angular/core';
import { TileComponent } from "../tile/tile.component";
import { TileStore } from '../../data/tile-store.service';
import { TileModel } from '../../models/tile.model';
import { NgFor } from '@angular/common';
import { PlayerStore } from '../../data/player-store.service';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [TileComponent, NgFor],
  templateUrl: './board.component.html',
  styleUrl: './board.component.css'
})
export class BoardComponent implements OnInit{
  // Dynamic immutable Tile data from the backend
  readonly tileStore = inject(TileStore);
  // Read-once immutable Player data from the backend
  readonly playerStore = inject(PlayerStore);

  ngOnInit(): void {
    this.tileStore.init();
    this.playerStore.init();
  }

  trackById = (_: number, t: TileModel) => t.id;
}
