package com.nastyhaze.homeworld.hwe_app.web.controller.rel;

import com.nastyhaze.homeworld.hwe_app.service.rel.RelEventPlayerService;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 *  API for RelEventPlayer entity & relationship data.
 */
@RestController
@RequestMapping("/api/rel")
@RequiredArgsConstructor
public class RelEventPlayerController {

    private final RelEventPlayerService eventPlayerService;

    /**
     * Returns Team, Player, and Scoring data via List of List<PlayerScoreResponse> objects. Blocking operation.
     * @param eventId
     * @return Mono<List<PlayerScoreResponse>>
     */
    @GetMapping(value = "/scoresByEvent/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<PlayerScoreResponse>> getPlayerScoresByEvent(@PathVariable Long eventId) {
        return Mono.fromCallable(() -> eventPlayerService.findPlayerScoresByEventId(eventId))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
