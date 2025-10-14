package com.nastyhaze.homeworld.hwe_app.web.controller.rel;

import com.nastyhaze.homeworld.hwe_app.service.rel.RelTeamTileService;
import com.nastyhaze.homeworld.hwe_app.web.request.TileUpdateRequest;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamTileResponse;
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
 *  API for RelTeamTile entity & relationship data.
 */
@RestController
@RequestMapping("/api/rel")
@RequiredArgsConstructor
public class RelTeamTileController {

    private final RelTeamTileService teamTileService;

    /**
     * Returns snapshot of stateful Team-Tile data.
     * @param teamId
     * @return Mono<TeamTileResponse>
     */
    @GetMapping(value = "/tilesByTeam/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<TeamTileResponse>> getTilesByTeam(@PathVariable Long teamId) {
        return Mono.fromCallable(() -> teamTileService.findAllByTeamId(teamId))
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Returns stream of stateful Team-Tile data.
     * @param teamId
     * @return Flux<TeamTileResponse>
     */
    @GetMapping(value = "/tilesByTeam/{teamId}/streaming", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<TeamTileResponse>> streamTilesByTeam(@PathVariable Long teamId) {
        return teamTileService.streamByTeamId(teamId);
    }

    /**
     * Returns a single, updated RelTeamTile response object.
     * @param relId
     * @param tileUpdateRequest
     * @return Mono<TeamTileResponse>
     */
    @PatchMapping(value = "/tilesByTeam/{relId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TeamTileResponse> update(@PathVariable Long relId, @RequestBody @Valid TileUpdateRequest tileUpdateRequest) {
        return Mono.fromCallable(() -> teamTileService.updateTile(relId, tileUpdateRequest))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
