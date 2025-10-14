import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EventResponse } from "../response/event-response";
import { endpoints } from "../../../../shared/api/endpoints";

/**
 *  HTTP Methods for endpoints returning Event data.
 */
@Injectable({ providedIn: 'root' })
export class EventApiService {
    private http = inject(HttpClient);

    
    // -- Readings Endpoints --
    // Retrieve static Event data (currently always static)
    getCurrentEventSnapshot(): Observable<EventResponse> {
        return this.http.get<EventResponse>(endpoints.events.snapshot);
    }
}