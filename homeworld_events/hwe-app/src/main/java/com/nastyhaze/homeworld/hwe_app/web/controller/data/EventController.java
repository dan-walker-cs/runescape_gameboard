package com.nastyhaze.homeworld.hwe_app.web.controller.data;

import com.nastyhaze.homeworld.hwe_app.service.data.EventService;
import com.nastyhaze.homeworld.hwe_app.web.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 *  API for Event entity data.
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * Returns the current (active) Event - or the default fallback Event - data.
     * This is a blocking operation.
     * @return Mono<EventResponse>
     */
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EventResponse> currentEvent() {
        return Mono.fromCallable(eventService::getCurrentEvent)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
