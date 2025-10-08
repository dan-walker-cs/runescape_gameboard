package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.Tile;
import com.nastyhaze.homeworld.hwe_app.repository.TileRepository;
import com.nastyhaze.homeworld.hwe_app.util.CommonUtils;
import com.nastyhaze.homeworld.hwe_app.web.mapper.TileMapper;
import com.nastyhaze.homeworld.hwe_app.web.request.TileRequest;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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

    private final TileRepository tileRepository;
    private final TileMapper tileMapper;
    private final PlayerService playerService;

    /**
     * Returns all active Tiles.
     * @return List<TileResponse>
     */
    public List<TileResponse> findAllTiles() {
        return tileRepository.findAll()
            .stream()
            .filter(AuditEntity::isActive) // TODO: Can filter at repository level instead
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
        System.out.println("Calling update on tile wtih ID: " + tileId);
        // Throw an Exception if the Request object is invalid
        validateRequest(tileRequest);

        Tile target = tileRepository.findById(tileId)
            .orElseThrow(); // TODO: Create custom exception class for TileReadException

        target.setReserved(tileRequest.isReserved());
        target.setReservedBy(tileRequest.isReserved() ? playerService.getByDisplayName(tileRequest.reservedBy()) : null);
        target.setCompleted(tileRequest.isCompleted());
        target.setCompletedBy(tileRequest.isCompleted() ? playerService.getByDisplayName(tileRequest.completedBy()) : null);

        Tile updated = tileRepository.save(target);

        return tileMapper.toResponse(updated);
    }

    /**
     * Tiles cannot be both reserved & completed.
     * Tiles should not have a *By field populated paired with a false is* field.
     * @param tileRequest
     */
    private void validateRequest(TileRequest tileRequest) {
        boolean isValid = isValidDefault(tileRequest) || (isValidCompleted(tileRequest) && isValidReserved(tileRequest));

        if (!isValid) throw new RuntimeException(); // TODO: Create custom exception class for TileUpdateException
    }

    /**
     * Tile Validation Helper.
     * Validates the isCompleted condition.
     * TODO: Eventually enforce isCompleted = true -> completedBy = non-null
     * @param tileRequest
     * @return boolean
     */
    private boolean isValidCompleted(TileRequest tileRequest) {
        return !tileRequest.isCompleted() ||
            (!tileRequest.isReserved() && CommonUtils.isNullOrBlankString(tileRequest.reservedBy()));
    }

    /**
     * Tile Validation Helper.
     * Validates the isReserved condition.
     * TODO: Eventually enforce isReserved = true -> reservedBy = non-null
     * @param tileRequest
     * @return boolean
     */
    private boolean isValidReserved(TileRequest tileRequest) {
        return !tileRequest.isReserved() ||
            (!tileRequest.isCompleted() && CommonUtils.isNullOrBlankString(tileRequest.completedBy()));
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
