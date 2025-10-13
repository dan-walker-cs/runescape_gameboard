package com.nastyhaze.homeworld.hwe_app.web.event;

import com.nastyhaze.homeworld.hwe_app.web.response.TeamTileResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * This class acts as a bridge between imperative & reactive code.
 */
@Component
public class TileEvent {
    // Allow multiple subscribers & buffer events on backpressure, rather than drop.
    private final Sinks.Many<TeamTileResponse> sink = Sinks.many().multicast().onBackpressureBuffer();

    /**
     * Push data into the reactive stream
     * @param event
     */
    public void emit(TeamTileResponse event) {
        sink.tryEmitNext(event);
    }

    /**
     * Pull data from the reactive stream
     * @return Flux<TeamTileResponse>
     */
    public Flux<TeamTileResponse> stream() {
        return sink.asFlux();
    }
}
