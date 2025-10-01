import { Component } from '@angular/core';
import { GameTile } from "../game-tile/game-tile.component";

@Component({
  selector: 'app-game-board',
  standalone: true,
  imports: [GameTile],
  templateUrl: './game-board.component.html',
  styleUrl: './game-board.component.css'
})
export class GameBoard {

}
