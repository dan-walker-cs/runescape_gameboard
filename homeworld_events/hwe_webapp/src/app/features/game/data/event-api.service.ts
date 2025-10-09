import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EventResponse } from "./response/event-response";
import { endpoints } from "../../../shared/api/endpoints";

@Injectable({ providedIn: 'root' })
export class EventApiService {

    private http = inject(HttpClient);

    // -- Readings Endpoints --
    // Retrieve initial state
    getCurrentEventSnapshot(): Observable<EventResponse> {
        return this.http.get<EventResponse>(endpoints.events.snapshot);
    }
}