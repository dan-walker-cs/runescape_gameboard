import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EventResponse } from "./response/event-response";
import { endpoints } from "../../../shared/api/endpoints";
import { EventPlayerTeamResponse } from "./response/event-player-team-response";

@Injectable({ providedIn: 'root' })
export class EventApiService {
    private http = inject(HttpClient);

    
    // -- Readings Endpoints --
    // Retrieve initial state
    getCurrentEventSnapshot(): Observable<EventResponse> {
        return this.http.get<EventResponse>(endpoints.events.snapshot);
    }

    // Retieves teams and their associated players given an eventId.
    getPlayerAndTeamSnapshot(eventId: number): Observable<EventPlayerTeamResponse> {
        return this.http.get<EventPlayerTeamResponse>(endpoints.events.playersByTeam(eventId));
    }
}