package com.nastyhaze.homeworld.hwe_app.web.controller.data;

import com.nastyhaze.homeworld.hwe_app.service.data.TileService;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
}
