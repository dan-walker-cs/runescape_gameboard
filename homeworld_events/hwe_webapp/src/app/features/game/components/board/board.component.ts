import { Component, DestroyRef, inject, Injector, OnInit } from '@angular/core';
import { TileComponent } from "../tile/tile.component";
import { TileStore } from '../../data/tile-store.service';
import { NgFor, NgStyle } from '@angular/common';
import { PlayerStore } from '../../data/player-store.service';
import { BoardStore } from '../../data/board-store.service';
import { GridTile } from '../../models/grid-tile';
import { firstValueFrom } from 'rxjs';
import { takeUntilDestroyed, toObservable } from '@angular/core/rxjs-interop';

@Component({
    selector: 'app-board',
    standalone: true,
    imports: [TileComponent, NgFor, NgStyle],
    templateUrl: './board.component.html',
    styleUrl: './board.component.css'
})
export class BoardComponent implements OnInit{  
    private injector   = inject(Injector);
    private destroyRef = inject(DestroyRef);

    // Dynamic immutable data from the backend
    readonly tileStore = inject(TileStore); 
    // Read-once immutable data from the backend
    readonly boardStore = inject(BoardStore);
    readonly playerStore = inject(PlayerStore);


    async ngOnInit(): Promise<void> {
        // Load snapshots
        await Promise.all([
            firstValueFrom(this.boardStore.init()),
            firstValueFrom(this.playerStore.init()),
            firstValueFrom(this.tileStore.init()),
        ]);

        // Subscribe to Tile updates
        toObservable(this.tileStore.tiles, { injector: this.injector })
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe();
    }

    // Hex Dimensions
    private readonly RADIUS = 32;
    get hexW(): number { return 2 * this.RADIUS }
    get hexH(): number { return Math.sqrt(3) * this.RADIUS; }

    // Ease-of-access
    gridBoard() { return this.boardStore.board(); } // ease-of-access
    gridTiles() { return this.boardStore.board()?.gridTileList ?? []; }

    // Determines where to draw Hex on screen based on Tile-Coordinates association.
    private hexToPixel(t: GridTile) {
        const q = t.qCoord, r = t.rCoord;
        const x = 1.5 * this.RADIUS * q;
        const y = Math.sqrt(3) * this.RADIUS * (r + q / 2);
        return { x, y: -y };
    }

    // Styling for the Tiles - too much math for my CSS skills
    tileStyle(gt: GridTile): Record<string,string> {
        const { x, y } = this.hexToPixel(gt);
        const bbox = this.getBBoxFromTiles();
        const cx = bbox?.cx ?? 0;
        const cy = bbox?.cy ?? 0;

        return {
            left: `${x - cx}px`,
            top: `${y - cy}px`,
            width: `${this.hexW}px`,
            height: `${this.hexH}px`,
        };
    }

    // // Styling for the Board - too much math for my CSS skills
    boardStyle(): Record<string,string> {
        const bbox = this.getBBoxFromTiles();
        if (!bbox) return { width: '100%', height: '100%' };

        return {
            width:  `${Math.ceil(bbox.width)}px`,
            height: `${Math.ceil(bbox.height)}px`,
            overflow: 'hidden'
        };
    }

    // Dynamic padding for the Board's "border-box"
    private getBBoxFromTiles() {
        const tiles = this.gridTiles();
        if (!tiles.length) return null;

        const centers = tiles.map(t => this.hexToPixel(t));
        const xs = centers.map(c => c.x);
        const ys = centers.map(c => c.y);

        const minX = Math.min(...xs), maxX = Math.max(...xs);
        const minY = Math.min(...ys), maxY = Math.max(...ys);

        // include hex half-size so edges aren’t clipped
        const width  = (maxX - minX) + this.hexW;
        const height = (maxY - minY) + this.hexH;

        const cx = (minX + maxX) / 2;
        const cy = (minY + maxY) / 2;

        return { width, height, cx, cy };
    }
}
