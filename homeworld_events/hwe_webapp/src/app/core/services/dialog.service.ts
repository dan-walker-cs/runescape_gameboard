import { Injectable, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TileModel } from '../../features/game/models/tile.model';
import { TileDialogComponent } from '../../features/game/components/tile-dialog/tile-dialog.component';
import { catchError, Observable, of, shareReplay, switchMap } from 'rxjs';
import { BoardInfoDialogComponent } from '../../features/game/components/board-info-dialog/board-info-dialog.component';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class DialogService {
    private http = inject(HttpClient);
    private readonly dialog = inject(MatDialog);

    private boardInfoHtml$: Observable<string>;
    
    constructor() {
        this.boardInfoHtml$ = this.http
            .get('assets/templates/2025_winter_board_info.html', { responseType: 'text' }) // TODO: constant
            .pipe(  
                catchError(() => of('<p>Unable to load board info.</p>')),
                shareReplay(1)
            );
    }

    /**
     * Configures & provides the MatDialog component for Tile actions.
     * @param tile 
     * @returns Observable<any>
     */
    openTileDialog(tile: TileModel): Observable<any> {
        return this.dialog
        .open(
            TileDialogComponent, 
            { 
                data: tile, 
                panelClass: 'tile-dialog-container' ,
                autoFocus: false,
                position: { top: '10em', right: '20em' }
            })
        .afterClosed();
    }

    /**
     * Configures & provides the MatDialog component for the Board Info icon action.
     * @returns Observable<any>
     */
    openBoardInfoDialog(): Observable<any> {
        return this.boardInfoHtml$.pipe(
            switchMap(boardInfoText =>
                this.dialog.open(BoardInfoDialogComponent, 
                    {
                        data: boardInfoText,
                        panelClass: 'board-info-dialog-container',
                        autoFocus: false,
                        position: { top: '10em', right: '20em' }
                    }
                ).afterClosed()
            )
        );
    }
}