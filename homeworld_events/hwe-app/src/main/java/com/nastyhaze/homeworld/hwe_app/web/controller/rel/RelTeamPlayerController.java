package com.nastyhaze.homeworld.hwe_app.web.controller.rel;

import com.nastyhaze.homeworld.hwe_app.service.rel.RelTeamPlayerService;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamPlayerResponse;
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
 *  API for RelTeamPlayer entity & relationship data.
 */
@RestController
@RequestMapping("/api/rel")
@RequiredArgsConstructor
public class RelTeamPlayerController {

    private final RelTeamPlayerService teamPlayerService;


    @GetMapping(value = "/playerTeamsByEvent/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<TeamPlayerResponse>> getPlayerTeamsByEvent(@PathVariable Long eventId) {
        return Mono.fromCallable(() -> teamPlayerService.findAllPlayerTeamsByEvent(eventId))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
