import { Injectable, OnDestroy, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TileResponse } from './response/tile-response';
import { TileRequest } from './request/tile-request';
import { ServerEventType } from '../../../shared/constant/server-event-type';
import { endpoints } from '../../../shared/api/endpoints';

@Injectable({ providedIn: 'root' })
export class TileApiService implements OnDestroy {

    private eventSource?: EventSource;
    private http = inject(HttpClient);

    // -- Readings Endpoints --
    // Retrieve initial state
    getTilesSnapshot(): Observable<TileResponse[]> {
        return this.http.get<TileResponse[]>(endpoints.tiles.snapshot);
    }

    // Retrieve reactive state
    getTilesStreaming(): Observable<TileResponse> {
        return new Observable<TileResponse>(subscriber => {
            this.eventSource = new EventSource(endpoints.tiles.stream);

            const onSnapshot = (e: MessageEvent) => subscriber.next(JSON.parse(e.data));
            const onUpdate   = (e: MessageEvent) => subscriber.next(JSON.parse(e.data));
            const onError    = (err: any) => subscriber.error?.(err);

            this.eventSource.addEventListener(ServerEventType.TILE_SNAPSHOT, onSnapshot);
            this.eventSource.addEventListener(ServerEventType.TILE_UDPATE, onUpdate);
            this.eventSource.onerror = onError; // EventSource auto-retries; can ignore or log, if needed

            return () => this.close();
        });
    }

    // -- Writing Endpoints --
    update(tileId: number, tileRequest: TileRequest): Observable<TileResponse> {
        return this.http.patch<TileResponse>(endpoints.tiles.update(tileId), tileRequest);
    }

    // Cleanup when dependent destroyed
    ngOnDestroy(): void {
        this.close();
    }

    // Destroy event channel
    close() {
        if (this.eventSource) {
            this.eventSource.close();
            this.eventSource = undefined;
        }
    }
}