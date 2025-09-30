import { Component } from '@angular/core';
import { URLS } from './links.constants';

@Component({
  selector: 'app-links',
  standalone: true,
  imports: [],
  templateUrl: './links.component.html',
  styleUrls: ['./links.component.css']
})
export class Links {
  readonly urls = URLS;
}