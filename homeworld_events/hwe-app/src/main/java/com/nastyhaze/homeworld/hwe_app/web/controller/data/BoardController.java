package com.nastyhaze.homeworld.hwe_app.web.controller.data;

import com.nastyhaze.homeworld.hwe_app.service.data.BoardService;
import com.nastyhaze.homeworld.hwe_app.web.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 *  API for Board entity data.
 */
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * Returns a Board based on the given Event id.
     * This is a blocking operation.
     * @return Mono<BoardResponse>
     */
    @GetMapping(value = "/byEvent/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BoardResponse> getBoardByEventId(@PathVariable Long eventId) {
        return Mono.fromCallable(() -> boardService.getBoardByEvent(eventId))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
