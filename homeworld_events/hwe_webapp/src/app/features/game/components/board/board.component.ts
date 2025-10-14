import { Component, computed, DestroyRef, inject, Injector, OnInit, signal } from '@angular/core';
import { TileComponent } from "../tile/tile.component";
import { NgFor, NgStyle } from '@angular/common';
import { BoardStore } from '../../data/store/board-store.service';
import { GridTile } from '../../models/grid-tile';
import { firstValueFrom } from 'rxjs';
import { takeUntilDestroyed, toObservable } from '@angular/core/rxjs-interop';
import { EventStore } from '../../data/store/event-store.service';
import { TileStore } from '../../data/store/tile-store.service';
import { TeamStore } from '../../data/store/team-store.service';
import { TileModel } from '../../models/tile.model';
import { TeamModel } from '../../models/team.model';

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
    readonly eventStore = inject(EventStore);
    readonly boardStore = inject(BoardStore);
    readonly teamStore = inject(TeamStore);

    // Signals
    selectedTeam = signal<number | null>(null);
    private selectedTeamId = computed<number | null>(() => {
        const teamId = this.selectedTeam();
        const team = this.teamStore.teams().find(t => t.id === teamId);
        return team ? team.id : null;
    });
    private tilesBySelectedTeam = computed(() => {
        console.log('START MAP GEN BOARD-COMPONENT FOR ID: ', this.selectedTeamId()); /** DEBUG */
        console.log('START MAP GEN BOARD-COMPONENT FROM TILES: ', this.tileStore.tiles()); /** DEBUG */
        const id = this.selectedTeamId();
        const map = new Map<number, TileModel>();

        if (id == null) return map;
        for (const t of this.tileStore.tiles()) {
            if (t.teamId === id) map.set(t.tileId, t);
        }
        console.log('Computed Tile Map: ', map);    /** DEBUG */
        return map;
    });

    async ngOnInit(): Promise<void> {
        // Load snapshots
        await Promise.all([
            firstValueFrom(this.eventStore.init()),
            firstValueFrom(this.teamStore.init()),
            firstValueFrom(this.boardStore.init()),
            firstValueFrom(this.tileStore.init()),
        ]);

        // Set Default team
        this.selectedTeam.set(this.teamStore.teams().at(0)?.id ?? null);
        console.log('BOARD-COMPONENT INIT - this.selectedTeam: ', this.selectedTeam());
        console.log('BOARD-COMPONENT INIT - this.selectedTeamId: ', this.selectedTeamId());

        // Subscribe to Tile updates
        toObservable(this.tileStore.tiles, { injector: this.injector })
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe();
    }

    // Determines number of Team tabs to render for the Board & provides their data
    getTeams(): TeamModel[] {
        return this.teamStore.teams();
    }

    // Fires when a Team selection is made on the Board Nav
    async selectTeam(teamId: number): Promise<void> {
        await firstValueFrom(this.tileStore.loadSnapshotByTeam(teamId));
        this.selectedTeam.set(teamId);
        //this.tileStore.startStreamFor(teamId);
    }

    // Rturns the target Tile data associated with the selected
    getTileByTeam(tileId: number): TileModel | null {
        //console.log('BOARD-COMPONENT - getTileByTeam Model: ', this.tilesBySelectedTeam().get(tileId)); /** DEBUG */
        return this.tilesBySelectedTeam().get(tileId) ?? null;
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

    // Styling for the Board - too much math for my raw CSS skills
    boardStyle(): Record<string,string> {
        const bbox = this.getBBoxFromTiles();
        if (!bbox) return { width: '100%', height: '100%' };

        return {
            width:  `${Math.ceil(bbox.width)}px`,
            height: `${Math.ceil(bbox.height)}px`,
            // overflow: 'hidden' // TODO: This is overflowing. I really just want to extend board to fill app__main..
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
