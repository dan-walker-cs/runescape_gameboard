package com.nastyhaze.homeworld.hwe_app.web.controller.rel;

import com.nastyhaze.homeworld.hwe_app.service.rel.RelBoardTileService;
import com.nastyhaze.homeworld.hwe_app.web.response.BoardTileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 *  API for RelBoardTile entity & relationship data.
 */
@RestController
@RequestMapping("/api/rel")
@RequiredArgsConstructor
public class RelBoardTileController {

    private final RelBoardTileService boardTileService;

    /**
     * Returns a single BoardTileResponse object containing:
     *      - boardId from request
     *      - A map of Tile-Coordinate associations to build a hex-grid from.
     * This is a blocking operation.
     * @param boardId
     * @return Mono<BoardTileResponse>
     */
    @GetMapping(value = "/gridTilesByBoard/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BoardTileResponse> getGridTilesByBoard(@PathVariable Long boardId) {
        return Mono.fromCallable(() -> boardTileService.getTilesByBoardId(boardId))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
