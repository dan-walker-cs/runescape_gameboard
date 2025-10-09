import { Component, inject, OnInit } from '@angular/core';
import { OVERVIEW_FIELDS } from './overview.constants';
import { EventStore } from '../game/data/event-store.service';
import { DatePipe } from '@angular/common'; 

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [ DatePipe ],
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit 
{
  readonly eventStore = inject(EventStore);

  readonly overview_fields = OVERVIEW_FIELDS;

  ngOnInit(): void {
    this.eventStore.init();
  }
}