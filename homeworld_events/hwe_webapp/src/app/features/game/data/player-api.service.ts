import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PlayerResponse } from "./response/player-response";
import { Observable } from "rxjs";
import { endpoints } from '../../../shared/api/endpoints';

@Injectable({ providedIn: 'root' })
export class PlayerApiService {
    private http = inject(HttpClient);


    // -- Readings Endpoints --
    // Retrieve initial state
    getPlayersSnapshot(): Observable<PlayerResponse[]> {
        return this.http.get<PlayerResponse[]>(endpoints.players.snapshot);
    }
}