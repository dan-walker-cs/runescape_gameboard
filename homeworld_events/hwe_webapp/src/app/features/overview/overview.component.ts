import { Component, inject, OnInit } from '@angular/core';
import { EventStore } from '../game/data/event-store.service';
import { DatePipe, NgFor } from '@angular/common'; 

@Component({
    selector: 'app-overview',
    standalone: true,
    imports: [DatePipe, NgFor],
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {
    readonly eventStore = inject(EventStore);

    ngOnInit(): void {
        this.eventStore.init();
    }
}