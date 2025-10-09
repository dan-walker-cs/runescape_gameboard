package com.nastyhaze.homeworld.hwe_app.web.controller;

import com.nastyhaze.homeworld.hwe_app.service.TileService;
import com.nastyhaze.homeworld.hwe_app.web.event.TileEvent;
import com.nastyhaze.homeworld.hwe_app.web.request.TileRequest;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 *  API for Tile entity data.
 */
@RestController
@RequestMapping("/api/tiles")
@RequiredArgsConstructor
public class TileController {

    private final TileService tileService;

    /**
     * One-shot snapshot (JSON). Runs on boundedElastic since it’s blocking.
     * @return Mono<List<TileResponse>>
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<TileResponse>> list() {
        return Mono.fromCallable(tileService::findAllTiles)
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Live stream of tile updates via SSE.
     * @return Flux<ServerSentEvent<TileResponse>>
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<TileResponse>> stream() {
        return tileService.stream();
    }

    /**
     * Update a single Tile.
     * @param id
     * @param tileRequest
     * @return Mono<TileResponse>
     */
    @PatchMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TileResponse> update(@PathVariable Long id, @RequestBody @Valid TileRequest tileRequest) {
        return Mono.fromCallable(() -> tileService.updateTile(id, tileRequest))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
