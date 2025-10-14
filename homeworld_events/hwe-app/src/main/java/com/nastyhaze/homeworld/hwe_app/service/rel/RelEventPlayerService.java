package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.rel.RelEventPlayerRepository;
import com.nastyhaze.homeworld.hwe_app.service.dto.EventTeamPlayerDTO;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelEventPlayerMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
}
