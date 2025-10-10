package com.nastyhaze.homeworld.hwe_app.web.controller.rel;

import com.nastyhaze.homeworld.hwe_app.service.rel.RelEventPlayerService;
import com.nastyhaze.homeworld.hwe_app.web.response.EventPlayerTeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 *  API for RelPlayerEvent entity & relationship data.
 */
@RestController
@RequestMapping("/api/rel")
@RequiredArgsConstructor
public class RelEventPlayerController {

    private final RelEventPlayerService eventPlayerService;

    /**
     * Returns a single EventPlayerTeamResponse object containing:
     *      - Event data associated with the provided eventId
     *      - A map of Team-Player associations
     * This is a blocking operation.
     * @@param eventId
     * @return Mono<EventPlayerTeamResponse>
     */
    @GetMapping(value = "/event/{eventId}/playersByTeam", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EventPlayerTeamResponse> getTeamPlayersByEvent(@PathVariable Long eventId) {
        return Mono.fromCallable(() -> eventPlayerService.getEventPlayerTeamsByEvent(eventId))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
