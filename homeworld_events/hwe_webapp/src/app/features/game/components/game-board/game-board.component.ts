import { Component, inject, Signal } from '@angular/core';
import { TileComponent } from "../tile/tile.component";
import { TileStore } from '../../data/tile-store.service';
import { TileModel } from '../../models/tile.model';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-game-board',
  standalone: true,
  imports: [TileComponent, NgFor],
  templateUrl: './game-board.component.html',
  styleUrl: './game-board.component.css'
})
export class GameBoard {
  private readonly store = inject(TileStore);
  tiles: Signal<TileModel[]> = this.store.tiles;
}
