import { Component, inject, OnInit } from "@angular/core";
import { EventStore } from "../game/data/event-store.service";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";
import { ERROR_HTML } from "../../shared/constant/common-constant";
import { toObservable } from "@angular/core/rxjs-interop";
import { catchError, debounceTime, filter, map, of, switchMap, timer } from "rxjs";

@Component({
  selector: 'app-rules',
  standalone: true,
  imports: [],
  templateUrl: './rules.component.html',
  styleUrls: [ './rules.component.css' ]
})
export class RulesComponent implements OnInit {
  readonly eventStore = inject(EventStore);
  rulesTemplate: SafeHtml = '';

  constructor(private http: HttpClient, private sanitize: DomSanitizer) {}

  ngOnInit(): void {
    this.eventStore.init();

    this.http.get(this.eventStore.event()?.rulesPath ?? ERROR_HTML, { responseType: 'text' })
      .subscribe(html => {
        this.rulesTemplate = this.sanitize.bypassSecurityTrustHtml(html);
      });
  }
}