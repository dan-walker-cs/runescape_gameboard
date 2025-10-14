package com.nastyhaze.homeworld.hwe_app.web.event;

import com.nastyhaze.homeworld.hwe_app.web.response.TeamTileResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class acts as a bridge between imperative & reactive code.
 */
@Component
public class TileEvent {
    // Allow multiple subscribers & buffer events on backpressure, rather than drop.
    // Create a separate data stream for each Team to prevent noise in the reactive updates.
    private final ConcurrentHashMap<Long, Sinks.Many<TeamTileResponse>> sinks = new ConcurrentHashMap<>();

    private Sinks.Many<TeamTileResponse> sink(Long teamId) {
        return sinks.computeIfAbsent(teamId, id -> Sinks.many().replay().latest());
    }

    /**
     * Push data into the reactive stream, based on Team id.
     * @param event
     */
    public void emit(TeamTileResponse event) {
        Sinks.EmitResult temp = sink(event.teamId()).tryEmitNext(event);
        if (temp.isFailure()) System.out.println("Emit failed: " + temp);
        System.out.println("Emit UPDATE successful.");
    }

    /**
     * Pull data from the reactive stream, based on Team id.
     * @param teamId
     * @return Flux<TeamTileResponse>
     */
    public Flux<TeamTileResponse> stream(Long teamId) {
        return sink(teamId).asFlux()
            .doOnSubscribe(s -> System.out.println("live stream subscribed for team: " + teamId))
            .doFinally(s -> System.out.println("live stream unsubscribed for team: " + teamId));
    }
}
