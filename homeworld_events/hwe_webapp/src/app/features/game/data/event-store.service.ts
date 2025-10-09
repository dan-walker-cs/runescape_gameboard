import { computed, Injectable, signal } from "@angular/core";
import { EventModel } from "../models/event.model";
import { EventResponse } from "./response/event-response";
import { EventApiService } from "./event-api.service";

@Injectable({ providedIn: 'root' })
export class EventStore {
    // Private, mutable store for use within this service
    // Signal: Angular's reactive state primitive
    private _event = signal<EventModel | null>(null);
    
    // Public, immutable store for use outside the service
    // Computed: Creates a derrived signal. Whenever the dependency signal changes, the computation is automatically re-run.
    readonly event = computed(() => this._event());

    constructor(private eventApi: EventApiService) {}

    // To be called by dependents in OnInit
    init(): void {
        this.eventApi.getCurrentEventSnapshot().subscribe({
            next: (eventResponse) => this._event.set(this._adaptResponseToModel(eventResponse)),
            error: (e) => console.error('[EventStore] snapshot failed', e),
        });
    }

        /**
         * Converts backend Response object format to frontend Model object format.
         * @param eventResponse 
         * @returns EventModel
         */
        private _adaptResponseToModel(eventResponse: EventResponse): EventModel {
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