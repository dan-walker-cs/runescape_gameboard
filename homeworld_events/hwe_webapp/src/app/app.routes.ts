import { Routes } from '@angular/router';
import { OverviewComponent } from './features/overview/overview.component';
import { LinksComponent } from './features/links/links.component';
import { BoardComponent } from './features/game/components/board/board.component';

export const routes: Routes = [
    { path: 'overview', component: OverviewComponent },
    { path: 'board', component: BoardComponent },
    { path: 'links', component: LinksComponent },
    { path: '**', redirectTo: 'overview' } // fallback to Overview as both landing & default
];