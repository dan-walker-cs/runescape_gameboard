package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelEventPlayer;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.rel.RelEventPlayerRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelEventPlayerMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.EventPlayerTeamResponse;
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
     * Returns a single EventPlayerTeamResponse object containing:
     *      - Event data associated with the provided eventId
     *      - A map of Team-Player associations
     * @return EventPlayerTeamResponse
     */
    public EventPlayerTeamResponse getEventPlayerTeamsByEvent(Long eventId) {
        List<RelEventPlayer> relEventPlayerList = eventPlayerRepository.findAllByActiveTrueAndEventId(eventId);
        if (relEventPlayerList.isEmpty()) throw new CrudServiceException(this.getClass().getName(), CrudOperationType.READ, eventId);

        return eventPlayerMapper.toEventPlayerTeamResponse(relEventPlayerList);
    }
}
