import { computed, inject, Injectable, signal } from "@angular/core";
import { EventModel } from "../../models/event.model";
import { EventResponse } from "../response/event-response";
import { map, Observable, shareReplay, take, tap } from "rxjs";
import { EventApiService } from "../api/event-api.service";

/**
 *  Central location to retrieve stateful Event data on the frontend.
 */
@Injectable({ providedIn: 'root' })
export class EventStore {
    private eventApi = inject(EventApiService);
    
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _event = signal<EventModel | null>(null);
    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly event = computed(() => this._event());  

    // Memo
    private init$?: Observable<EventModel>;


    // To be called by dependents in OnInit
    init(): Observable<EventModel> {
        if (!this.init$) {
            this.init$ = this.eventApi.getCurrentEventSnapshot()
                .pipe(
                    map(eventResponse => this._adaptEventResponseToModel(eventResponse)),
                    tap(eventModel => this._event.set(eventModel)),

                    take(1),
                    shareReplay(1)
                );
            }

        return this.init$;
    }

    /**
     * Converts backend EventResponse object format to frontend EventModel object format.
     * @param eventResponse 
     * @returns EventModel
     */
    private _adaptEventResponseToModel(eventResponse: EventResponse): EventModel {
        return {
            id: eventResponse.id,
            title: eventResponse.title,
            startDt: eventResponse.startDt,
            endDt: eventResponse.endDt,
            buyIn: eventResponse.buyIn,
            rulesPath: eventResponse.rulesPath
        };
    }
}