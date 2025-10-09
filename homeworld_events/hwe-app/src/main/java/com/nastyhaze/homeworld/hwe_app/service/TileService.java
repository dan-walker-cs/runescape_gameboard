package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.constant.ServerEventType;
import com.nastyhaze.homeworld.hwe_app.domain.data.Tile;
import com.nastyhaze.homeworld.hwe_app.exception.TileServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.TileRepository;
import com.nastyhaze.homeworld.hwe_app.util.CommonUtils;
import com.nastyhaze.homeworld.hwe_app.web.event.TileEvent;
import com.nastyhaze.homeworld.hwe_app.web.mapper.TileMapper;
import com.nastyhaze.homeworld.hwe_app.web.request.TileRequest;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
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
 *  Service layer between TileController & TileRepository.
 *  Provides logic to retrieve and mutate repository data for the API.
 *  This layer is BLOCKING & NON-REACTIVE.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TileService {

    private final TileEvent tileEvent;
    private final TileRepository tileRepository;
    private final TileMapper tileMapper;

    private final PlayerService playerService;

    /**
     * Provides a reactive data stream of the initial data state, live updates, and keep-alive heartbeat.
     * @return Flux<ServerSentEvent<TileResponse>>
     */
    public Flux<ServerSentEvent<TileResponse>> stream() {
        return Flux.merge(
            streamSnapshot(),
            streamLive(),
            streamHeartBeat()
        );
    }

    /**
     * Returns all active Tiles.
     * @return List<TileResponse>
     */
    public List<TileResponse> findAllTiles() {
        return tileRepository.findAllByActiveTrue()
            .stream()
            .map(tileMapper::toResponse)
            .toList();
    }

    /**
     * Update single Tile from TileRequest.
     * @param tileId
     * @param tileRequest
     * @return TileResponse
     */
    @Transactional
    public TileResponse updateTile(Long tileId, TileRequest tileRequest) {
        // Throw an Exception if the Request object is invalid
        validateRequest(tileId, tileRequest);

        Tile target = tileRepository.findById(tileId)
            .orElseThrow(() -> new TileServiceException(tileId, CrudOperationType.READ));

        target.setReserved(tileRequest.isReserved());
        target.setReservedBy(tileRequest.isReserved() ? playerService.findByDisplayName(tileRequest.reservedBy()) : null);
        target.setCompleted(tileRequest.isCompleted());
        target.setCompletedBy(tileRequest.isCompleted() ? playerService.findByDisplayName(tileRequest.completedBy()) : null);

        Tile updated = tileRepository.save(target);

        return tileMapper.toResponse(updated);
    }

    /**
     * Provides an initial data state for subscribers.
     * @return Flux<ServerSentEvent<TileResponse>>
     */
    private Flux<ServerSentEvent<TileResponse>> streamSnapshot() {
        return Mono.fromCallable(this::findAllTiles)
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(Flux::fromIterable)
            .map(response -> ServerSentEvent.builder(response)
                .event(ServerEventType.TILE_SNAPSHOT.desc())
                .build());
    }

    /**
     * Provides a live stream of updates for subscribers.
     * @return Flux<ServerSentEvent<TileResponse>>
     */
    private Flux<ServerSentEvent<TileResponse>> streamLive() {
        return tileEvent.stream()
            .map(response -> ServerSentEvent.builder(response)
                .event(ServerEventType.TILE_UPDATE.desc())
                .build());
    }

    /**
     * Provides a keep-alive heartbeat to avoid closing communications during down-time.
     * @return Flux<ServerSentEvent<TileResponse>>
     */
    private Flux<ServerSentEvent<TileResponse>> streamHeartBeat() {
        return Flux.interval(Duration.ofSeconds(15))
            .map(i -> ServerSentEvent.<TileResponse>builder()
                .comment(ServerEventType.HEARTBEAT.desc())
                .build());
    }

    /**
     * Tiles cannot be both reserved & completed.
     * Tiles should not have a *By field populated paired with a false is* field.
     * @param tileRequest
     */
    private void validateRequest(Long tileId, TileRequest tileRequest) {
        boolean isValid = isValidDefault(tileRequest) || (isValidCompleted(tileRequest) && isValidReserved(tileRequest));

        if (!isValid) throw new TileServiceException(tileId, CrudOperationType.UPDATE);
    }

    /**
     * Tile Validation Helper.
     * Validates the isCompleted condition.
     * @param tileRequest
     * @return boolean
     */
    private boolean isValidCompleted(TileRequest tileRequest) {
        boolean cleanReservedData = !tileRequest.isReserved() && CommonUtils.isNullOrBlankString(tileRequest.reservedBy());
        boolean validCompletedData = tileRequest.isCompleted() && !CommonUtils.isNullOrBlankString(tileRequest.completedBy());

        return !tileRequest.isCompleted() || (cleanReservedData && validCompletedData);
    }

    /**
     * Tile Validation Helper.
     * Validates the isReserved condition.
     * @param tileRequest
     * @return boolean
     */
    private boolean isValidReserved(TileRequest tileRequest) {
        boolean cleanCompletedData = !tileRequest.isCompleted() && CommonUtils.isNullOrBlankString(tileRequest.completedBy());
        boolean validReservedData = tileRequest.isReserved() && !CommonUtils.isNullOrBlankString(tileRequest.reservedBy());

        return !tileRequest.isReserved() || (cleanCompletedData && validReservedData);
    }

    /**
     * Tile Validation Helper.
     * Validates the default condition.
     * @param tileRequest
     * @return boolean
     */
    private boolean isValidDefault(TileRequest tileRequest) {
        return !tileRequest.isCompleted() && !tileRequest.isReserved() &&
            CommonUtils.isNullOrBlankString(tileRequest.completedBy()) && CommonUtils.isNullOrBlankString(tileRequest.reservedBy());
    }
}
