import { Component } from '@angular/core';
import { OVERVIEW_FIELDS } from './overview.constants';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [],
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class Overview {
  readonly overview_fields = OVERVIEW_FIELDS;
}