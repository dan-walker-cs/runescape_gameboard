import { Component, inject, Signal } from '@angular/core';
import { GameTile } from "../game-tile/game-tile.component";
import { TileStore } from '../../data/tile-store.service';
import { Tile } from '../../models/tile.model';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-game-board',
  standalone: true,
  imports: [GameTile, NgFor],
  templateUrl: './game-board.component.html',
  styleUrl: './game-board.component.css'
})
export class GameBoard {
  private readonly store = inject(TileStore);
  tiles: Signal<Tile[]> = this.store.tiles;
}
