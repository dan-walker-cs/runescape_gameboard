import { Routes } from '@angular/router';
import { Overview } from './features/overview/overview.component';
import { Links } from './features/links/links.component';
import { GameBoard } from './features/game-board/game-board.component';

export const routes: Routes = [
    { path: 'overview', component: Overview },
    { path: 'board', component: GameBoard },
    { path: 'links', component: Links },
    { path: '**', redirectTo: 'overview' } // fallback to Overview as both landing & default
];