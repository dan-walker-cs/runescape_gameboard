import { Component, inject, OnInit } from '@angular/core';
import { DatePipe, NgFor } from '@angular/common'; 
import { firstValueFrom } from 'rxjs';
import { TeamStore } from '../game/data/store/team-store.service';
import { EventStore } from '../game/data/store/event-store.service';

@Component({
    selector: 'app-overview',
    standalone: true,
    imports: [DatePipe, NgFor],
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {
    // Dynamic immutable data from the backend
    readonly eventStore = inject(EventStore);
    readonly teamStore = inject(TeamStore);

    async ngOnInit(): Promise<void> {
        await Promise.all([
            firstValueFrom(this.eventStore.init()),
            firstValueFrom(this.teamStore.init())
        ]);
    }
}