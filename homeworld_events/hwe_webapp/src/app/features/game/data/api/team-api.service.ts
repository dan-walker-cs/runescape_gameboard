import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { endpoints } from "../../../../shared/api/endpoints";
import { TeamPlayerResponse } from "../response/team-player-response";

/**
 *  HTTP Methods for endpoints returning Team data.
 */
@Injectable({ providedIn: 'root' })
export class TeamApiService {
    private http = inject(HttpClient);


    // -- Readings Endpoints --
    // Retrieve static Player & Team data (currently always static)
    getPlayerTeamsSnapshotByEvent(eventId: number): Observable<TeamPlayerResponse[]> {
        return this.http.get<TeamPlayerResponse[]>(endpoints.players.snapshotByTeam(eventId));
    }
}