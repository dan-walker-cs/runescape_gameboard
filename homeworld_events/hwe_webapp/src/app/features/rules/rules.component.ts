import { Component, inject, OnDestroy, OnInit } from "@angular/core";
import { ERROR_HTML } from "../../shared/constant/common-constant";
import { EventStore } from "../game/data/event-store.service";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";
import { catchError, firstValueFrom, of, Subject, takeUntil } from "rxjs";

@Component({
    selector: 'app-rules',
    standalone: true,
    imports: [],
    templateUrl: './rules.component.html',
    styleUrls: [ './rules.component.css' ]
})
export class RulesComponent implements OnInit, OnDestroy { 
    private destroy$ = new Subject<void>();

    readonly eventStore = inject(EventStore); 
    rulesTemplateClean: SafeHtml = ''; 

    constructor(private http: HttpClient, private sanitize: DomSanitizer) {} 

    async ngOnInit(): Promise<void> {
        const eventModel = await firstValueFrom(this.eventStore.init());

        this.http.get(eventModel.rulesPath, { responseType: 'text' })
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => of(ERROR_HTML))
            )
            .subscribe(rulesTemplateRaw => {
                this.rulesTemplateClean = this.sanitize.bypassSecurityTrustHtml(rulesTemplateRaw);
            });
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}