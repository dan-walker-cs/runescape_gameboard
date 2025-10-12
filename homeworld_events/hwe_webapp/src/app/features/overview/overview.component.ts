import { Component, inject, OnInit } from '@angular/core';
import { EventStore } from '../game/data/event-store.service';
import { DatePipe, NgFor } from '@angular/common'; 
import { firstValueFrom } from 'rxjs';

@Component({
    selector: 'app-overview',
    standalone: true,
    imports: [DatePipe, NgFor],
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

    readonly eventStore = inject(EventStore); 

    async ngOnInit(): Promise<void> {
        await firstValueFrom(this.eventStore.init());
    }
}