package com.nastyhaze.homeworld.hwe_app.web.controller;

import com.nastyhaze.homeworld.hwe_app.service.TileService;
import com.nastyhaze.homeworld.hwe_app.web.event.TileEvent;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

/**
 *  API for Tile entity data.
 */
@RestController
@RequestMapping("/api/tiles")
@RequiredArgsConstructor
public class TileController {

    private final TileService tileService;
    private final TileEvent tileEvent;

    /**
     * One-shot snapshot (JSON). Runs on boundedElastic since it’s blocking.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<TileResponse>> list() {
        return Mono.fromCallable(tileService::findAllTiles)
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Live stream of tile updates via SSE.
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<TileResponse>> stream() {
        // Initial snapshot (so new subscribers get current state)
        Flux<ServerSentEvent<TileResponse>> initial =
            Mono.fromCallable(tileService::findAllTiles)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(response -> ServerSentEvent.builder(response)
                    .event("tile-snapshot") // TODO: Move tile event type into an ENUM
                    .build());

        // Live updates from the sink
        Flux<ServerSentEvent<TileResponse>> updates =
            tileEvent.stream()
                .map(response -> ServerSentEvent.builder(response)
                    .event("tile-update") // TODO: Move tile event type into an ENUM
                    .build());

        // Keepalive heartbeats so intermediaries don’t close idle connections
        Flux<ServerSentEvent<TileResponse>> heartbeats =
            Flux.interval(Duration.ofSeconds(15))
                .map(i -> ServerSentEvent.<TileResponse>builder()
                    .comment("keepalive") // TODO: Move tile event type into an ENUM
                    .build());

        return Flux.merge(initial, updates, heartbeats);
    }
}
