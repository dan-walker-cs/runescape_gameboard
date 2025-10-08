import { Component, inject, OnInit, Signal } from '@angular/core';
import { TileComponent } from "../tile/tile.component";
import { TileStore } from '../../data/tile-store.service';
import { TileModel } from '../../models/tile.model';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [TileComponent, NgFor],
  templateUrl: './board.component.html',
  styleUrl: './board.component.css'
})
export class BoardComponent implements OnInit{
  readonly store = inject(TileStore);

  ngOnInit(): void {
    this.store.init();
  }

  trackById = (_: number, t: TileModel) => t.id;
}
