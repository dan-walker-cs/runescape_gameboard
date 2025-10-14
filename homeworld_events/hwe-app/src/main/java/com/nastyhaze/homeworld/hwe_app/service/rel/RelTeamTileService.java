package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.constant.ServerEventType;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamTile;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.rel.RelTeamTileRepository;
import com.nastyhaze.homeworld.hwe_app.service.data.PlayerService;
import com.nastyhaze.homeworld.hwe_app.util.CommonUtils;
import com.nastyhaze.homeworld.hwe_app.web.event.TileEvent;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelTeamTileMapper;
import com.nastyhaze.homeworld.hwe_app.web.request.TileUpdateRequest;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamTileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

/**
 *  Service layer between RelTeamTile Controller & EventRepository.
 *  Provides logic to retrieve and mutate repository Team-Tile relationship data for the UI.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelTeamTileService {

    private final RelTeamTileRepository teamTileRepository;
    private final RelTeamTileMapper teamTileMapper;

    private final TileEvent tileEvent;
    private final PlayerService playerService;
    private final RelEventPlayerService eventPlayerService;

    /**
     * Returns a TeamTileResponse containing:
     *      - Team id & name data associated with given teamId
     *      - Tile & RelTeamTile data associated with stateful tiles of given teamId.
     * @param teamId
     * @return TeamTileResponse
     */
    public List<TeamTileResponse> findAllByTeamId(Long teamId) {
        List<RelTeamTile> teamTileList = teamTileRepository.findAllByTeamIdAndActiveTrue(teamId);
        return teamTileMapper.toResponseList(teamTileList);
    }

    /**
     * Provides a reactive data stream of the initial data state, live updates, and keep-alive heartbeat.
     * @return Flux<ServerSentEvent<TeamTileResponse>>
     */
    public Flux<ServerSentEvent<TeamTileResponse>> streamByTeamId(Long teamId) {
        return Flux.merge(
            streamSnapshot(teamId),
            streamLive(teamId),
            streamHeartBeat()
        );
    }

    /**
     * Update single Tile from TileRequest.
     * @param relId
     * @param tileUpdateRequest
     * @return TileResponse
     */
    @Transactional
    public TeamTileResponse updateTile(Long relId, TileUpdateRequest tileUpdateRequest) {
        // Throw an Exception if the Request object is invalid
        validateRequest(relId, tileUpdateRequest);

        RelTeamTile target = teamTileRepository.findById(relId)
            .orElseThrow(() -> new CrudServiceException(this.getClass().getName(), CrudOperationType.READ, RelTeamTile.class.getName(), relId));

        // Determines if a score update is needed & applies if so
        eventPlayerService.updatePlayerScoreByEvent(target, tileUpdateRequest);

        target.setIsReserved(tileUpdateRequest.isReserved());
        target.setReservedBy(tileUpdateRequest.isReserved() ? playerService.findByDisplayName(tileUpdateRequest.reservedBy()) : null);
        target.setIsCompleted(tileUpdateRequest.isCompleted());
        target.setCompletedBy(tileUpdateRequest.isCompleted() ? playerService.findByDisplayName(tileUpdateRequest.completedBy()) : null);

        RelTeamTile updated = teamTileRepository.save(target);
        TeamTileResponse response = teamTileMapper.toResponse(updated);

        tileEvent.emit(response);

        return response;
    }

    /**
     * Provides an initial data state for subscribers.
     * @return Flux<ServerSentEvent<TeamTileResponse>>
     */
    private Flux<ServerSentEvent<TeamTileResponse>> streamSnapshot(Long teamId) {
        return Mono.fromCallable(() -> findAllByTeamId(teamId))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(Flux::fromIterable)
            .map(response -> ServerSentEvent.builder(response)
                .event(ServerEventType.TILE_SNAPSHOT.desc())
                .build());
    }

    /**
     * Provides a live stream of updates for subscribers.
     * @return Flux<ServerSentEvent<TeamTileResponse>>
     */
    private Flux<ServerSentEvent<TeamTileResponse>> streamLive(Long teamId) {
        return tileEvent.stream(teamId)
            .map(response -> ServerSentEvent.builder(response)
                .event(ServerEventType.TILE_UPDATE.desc())
                .build());
    }

    /**
     * Provides a keep-alive heartbeat to avoid closing communications during down-time.
     * @return Flux<ServerSentEvent<TeamTileResponse>>
     */
    private Flux<ServerSentEvent<TeamTileResponse>> streamHeartBeat() {
        return Flux.interval(Duration.ofSeconds(15))
            .map(i -> ServerSentEvent.<TeamTileResponse>builder()
                .comment(ServerEventType.HEARTBEAT.desc())
                .build());
    }

    /**
     * Tiles cannot be both reserved & completed.
     * Tiles should not have a *By field populated paired with a false is* field.
     * @param tileUpdateRequest
     */
    private void validateRequest(Long relId, TileUpdateRequest tileUpdateRequest) {
        boolean isValid = isValidDefault(tileUpdateRequest) || (isValidCompleted(tileUpdateRequest) && isValidReserved(tileUpdateRequest));

        if (!isValid) throw new CrudServiceException(this.getClass().getName(), CrudOperationType.UPDATE, RelTeamTile.class.getName(), relId);
    }

    /**
     * Tile Validation Helper.
     * Validates the isCompleted condition.
     * @param tileUpdateRequest
     * @return boolean
     */
    private boolean isValidCompleted(TileUpdateRequest tileUpdateRequest) {
        boolean cleanReservedData = !tileUpdateRequest.isReserved() && CommonUtils.isNullOrBlankString(tileUpdateRequest.reservedBy());
        boolean validCompletedData = tileUpdateRequest.isCompleted() && !CommonUtils.isNullOrBlankString(tileUpdateRequest.completedBy());

        return !tileUpdateRequest.isCompleted() || (cleanReservedData && validCompletedData);
    }

    /**
     * Tile Validation Helper.
     * Validates the isReserved condition.
     * @param tileUpdateRequest
     * @return boolean
     */
    private boolean isValidReserved(TileUpdateRequest tileUpdateRequest) {
        boolean cleanCompletedData = !tileUpdateRequest.isCompleted() && CommonUtils.isNullOrBlankString(tileUpdateRequest.completedBy());
        boolean validReservedData = tileUpdateRequest.isReserved() && !CommonUtils.isNullOrBlankString(tileUpdateRequest.reservedBy());

        return !tileUpdateRequest.isReserved() || (cleanCompletedData && validReservedData);
    }

    /**
     * Tile Validation Helper.
     * Validates the default condition.
     * @param tileUpdateRequest
     * @return boolean
     */
    private boolean isValidDefault(TileUpdateRequest tileUpdateRequest) {
        return !tileUpdateRequest.isCompleted() && !tileUpdateRequest.isReserved() &&
            CommonUtils.isNullOrBlankString(tileUpdateRequest.completedBy()) && CommonUtils.isNullOrBlankString(tileUpdateRequest.reservedBy());
    }

    /**
     * If a Tile is being updated to the Completed State, add score to player's total.
     * @param relEntity
     * @param updateRequest
     * @return boolean
     */
    private boolean isCompletingTile(RelTeamTile relEntity, TileUpdateRequest updateRequest) {
        return !relEntity.getIsCompleted() && updateRequest.isCompleted();
    }

    /**
     * If a Tile is being reverted from the Completed State, remove score from player's total.
     * @param relEntity
     * @param updateRequest
     * @return boolean
     */
    private boolean isUnCompletingTile(RelTeamTile relEntity, TileUpdateRequest updateRequest) {
        return relEntity.getIsCompleted() && !updateRequest.isCompleted();
    }
}
