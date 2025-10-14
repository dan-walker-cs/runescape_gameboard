package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.domain.data.Player;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelEventPlayer;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamTile;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.rel.RelEventPlayerRepository;
import com.nastyhaze.homeworld.hwe_app.service.dto.EventTeamPlayerDTO;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelEventPlayerMapper;
import com.nastyhaze.homeworld.hwe_app.web.request.TileUpdateRequest;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

/**
 *  Service layer between RelEventPlayer Controller & EventRepository.
 *  Provides logic to retrieve and mutate repository Event-Player relationship data for the UI.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelEventPlayerService {

    private final RelEventPlayerRepository eventPlayerRepository;
    private final RelEventPlayerMapper eventPlayerMapper;

    /**
     * Returns a list of PlayerScoreResponse objects containing Team, Player, and Scoring information.
     * @param eventId
     * @return List<PlayerScoreResponse>
     */
    public List<PlayerScoreResponse> findPlayerScoresByEventId(Long eventId) {
        List<EventTeamPlayerDTO> eventTeamPlayerDTOList = eventPlayerRepository.findEventTeamPlayerByEventIdAndActiveTrue(eventId);
        if (eventTeamPlayerDTOList.isEmpty()) throw new CrudServiceException(this.getClass().getName(), CrudOperationType.READ, eventId);

        return eventPlayerMapper.toPlayerScoreResponseList(eventTeamPlayerDTOList);
    }

    /**
     * Handles update conditions for RelEventPlayer.score.
     * @param target
     * @param tileUpdateRequest
     */
    @Transactional
    public void updatePlayerScoreByEvent(RelTeamTile target, TileUpdateRequest tileUpdateRequest) {
        final int weight = target.getTile().getWeight();
        final Long eventId = tileUpdateRequest.eventId();

        final Player oldPlayer = target.getCompletedBy();
        final boolean wasCompleted = Objects.nonNull(oldPlayer);

        final boolean nowCompleted = tileUpdateRequest.isCompleted();
        final String newPlayerName = tileUpdateRequest.completedBy();

        // No-op
        if (!wasCompleted && !nowCompleted) return;

        // Completion
        if (!wasCompleted && nowCompleted) {
            addScore(weight, eventId, newPlayerName);
            return;
        }

        // Un-completion
        if (wasCompleted && !nowCompleted) {
            subtractScore(weight, eventId, oldPlayer);
            return;
        }

        // Updating Completer
        if (wasCompleted && nowCompleted && !Objects.equals(oldPlayer.getDisplayName(), newPlayerName)) {
            subtractScore(weight, eventId, oldPlayer);
            addScore(weight, eventId, newPlayerName);
        }
    }

    /**
     * Adds incoming tileWeight to a RelEventPlayer entity's score.
     * @param tileWeight
     * @param eventId
     * @param targetPlayerName
     */
    private void addScore(int tileWeight, Long eventId, String targetPlayerName) {
        RelEventPlayer existing = eventPlayerRepository.findOneByEvent_IdAndPlayer_DisplayNameIgnoreCase(eventId, targetPlayerName)
            .orElseThrow(() -> new CrudServiceException(this.getClass().getName(), CrudOperationType.READ));

        existing.setScore(existing.getScore() + tileWeight);
        eventPlayerRepository.save(existing);
    }

    /**
     * Subtracts a RelEventPlayer entity's score by incoming tileWeight.
     * @param tileWeight
     * @param eventId
     * @param targetPlayer
     */
    private void subtractScore(int tileWeight, Long eventId, Player targetPlayer) {
        RelEventPlayer existing = eventPlayerRepository.findOneByEventIdAndPlayerId(eventId, targetPlayer.getId())
            .orElseThrow(() -> new CrudServiceException(this.getClass().getName(), CrudOperationType.READ));

        existing.setScore(existing.getScore() - tileWeight);
        eventPlayerRepository.save(existing);
    }
}
