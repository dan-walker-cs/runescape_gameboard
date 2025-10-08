import { Injectable, OnDestroy, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TileResponse } from './response/tile-response';
import { TileRequest } from './request/tile-request';

@Injectable({ providedIn: 'root' })
export class TileApiService implements OnDestroy {

    private eventSource?: EventSource;
    private http = inject(HttpClient);

    // -- Readings Endpoints --
    // Retrieve initial state
    getTilesBlocking(): Observable<TileResponse[]> {
        return this.http.get<TileResponse[]>('/api/tiles');
    }

    // Retrieve reactive state
    getTilesStreaming(): Observable<TileResponse> {
        return new Observable<TileResponse>(subscriber => {
            this.eventSource = new EventSource('/api/tiles/stream'); // TODO: Replace with constant

            const onSnapshot = (e: MessageEvent) => subscriber.next(JSON.parse(e.data));
            const onUpdate   = (e: MessageEvent) => subscriber.next(JSON.parse(e.data));
            const onError    = (err: any) => subscriber.error?.(err);

            this.eventSource.addEventListener('tile-snapshot', onSnapshot); // TODO: Create enum
            this.eventSource.addEventListener('tile-update', onUpdate); // TODO: Create enum
            this.eventSource.onerror = onError; // EventSource auto-retries; can ignore of log, if needed

            return () => this.close();
        });
    }

    // -- Writing Endpoints --
    update(tileId: number, tileRequest: TileRequest): Observable<TileResponse> {
        return this.http.patch<TileResponse>(`/api/tiles/${tileId}/update`, tileRequest); // TODO: Replace with constant
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