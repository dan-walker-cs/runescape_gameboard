package com.nastyhaze.homeworld.hwe_app.web.controller.data;

import com.nastyhaze.homeworld.hwe_app.service.data.PlayerService;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 *  API for Player entity data.
 */
@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    /**
     * One-shot snapshot (JSON). Runs on boundedElastic since it’s blocking.
     * @return Mono<List<TileResponse>>
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<PlayerResponse>> list() {
        return Mono.fromCallable(playerService::findAllPlayers)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
