import { Injectable, OnDestroy, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TileResponse } from './response/tile-response';

@Injectable({ providedIn: 'root' })
export class TileApiService implements OnDestroy {

    private eventSource?: EventSource;
    private http = inject(HttpClient);

    getTilesBlocking(): Observable<TileResponse[]> {
        return this.http.get<TileResponse[]>('/api/tiles');
    }

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

    close() {
        if (this.eventSource) {
            this.eventSource.close();
            this.eventSource = undefined;
        }
  }

    ngOnDestroy(): void {
        this.close();
    }
}