import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { EventStore } from '../game/data/event-store.service';
import { DatePipe, NgFor } from '@angular/common'; 
import { firstValueFrom, Subject } from 'rxjs';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-overview',
    standalone: true,
    imports: [DatePipe, NgFor],
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit, OnDestroy {
    private destroy$ = new Subject<void>();

    readonly eventStore = inject(EventStore); 
    rulesTemplateClean: SafeHtml = ''; 

    async ngOnInit(): Promise<void> {
        await firstValueFrom(this.eventStore.init());
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}