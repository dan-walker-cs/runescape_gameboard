import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TileResponse } from '../response/tile-response';
import { endpoints } from '../../../../shared/api/endpoints';
import { ServerEventType } from '../../../../shared/constant/server-event-type';
import { TileUpdateRequest } from '../request/tile-update-request';

@Injectable({ providedIn: 'root' })
export class TileApiService {
    private http = inject(HttpClient);  


    // -- Readings Endpoints --
    // Retrieve initial state
    getTilesSnapshotByTeam(teamId: number): Observable<TileResponse[]> {
        return this.http.get<TileResponse[]>(endpoints.tiles.snapshotByTeam(teamId));
    }

    // Retrieve reactive state
    getTilesStreamingByTeam(teamId: number): Observable<TileResponse> {
        return new Observable<TileResponse>(subscriber => {
            const eventSource = new EventSource(endpoints.tiles.streamingByTeam(teamId));

            const onSnapshot = (e: MessageEvent) => subscriber.next(JSON.parse(e.data));
            const onUpdate   = (e: MessageEvent) => subscriber.next(JSON.parse(e.data));
            const onError    = (err: any) => subscriber.error?.(err);

            eventSource.addEventListener(ServerEventType.TILE_SNAPSHOT, onSnapshot);
            eventSource.addEventListener(ServerEventType.TILE_UPDATE, onUpdate);
            eventSource.onerror = onError;

            return () => eventSource.close();
        });
    }

    // -- Writing Endpoints --
    update(relId: number, tileRequest: TileUpdateRequest): Observable<TileResponse> {
        return this.http.patch<TileResponse>(endpoints.tiles.update(relId), tileRequest);
    }
}