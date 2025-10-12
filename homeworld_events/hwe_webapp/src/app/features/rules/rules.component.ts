import { Component, inject, OnInit } from "@angular/core";
import { ERROR_HTML } from "../../shared/constant/common-constant";
import { EventStore } from "../game/data/event-store.service";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";
import { catchError, firstValueFrom, of, take } from "rxjs";

@Component({
    selector: 'app-rules',
    standalone: true,
    imports: [],
    templateUrl: './rules.component.html',
    styleUrls: [ './rules.component.css' ]
})
export class RulesComponent implements OnInit {
    private http = inject(HttpClient);
    private sanitize = inject(DomSanitizer);

    readonly eventStore = inject(EventStore); 
    rulesTemplateClean: SafeHtml = ''; 


    async ngOnInit(): Promise<void> {
        // Load snapshot
        const eventModel = await firstValueFrom(this.eventStore.init());

        // Inject HTML template
        this.http.get(eventModel.rulesPath, { responseType: 'text' })
            .pipe(
                catchError(() => of(ERROR_HTML)),
                take(1)
            )
            .subscribe(rulesTemplateRaw => {
                this.rulesTemplateClean = this.sanitize.bypassSecurityTrustHtml(rulesTemplateRaw);
            });
    }
}