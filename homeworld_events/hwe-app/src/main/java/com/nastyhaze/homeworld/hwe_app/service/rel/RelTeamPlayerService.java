package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamPlayer;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.rel.RelTeamPlayerRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelTeamPlayerMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamPlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 *  Service layer between RelTeamPlayer Controller & EventRepository.
 *  Provides logic to retrieve and mutate repository Team-Player relationship data for the UI.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelTeamPlayerService {

    private final RelTeamPlayerRepository teamPlayerRepository;
    private final RelTeamPlayerMapper teamPlayerMapper;

    /**
     * Returns a list of TeamPlateResponse obejcts given an EventId - containing teamId & a list of player names each.
     * @param eventId
     * @return List<TeamPlayerResponse>
     */
    public List<TeamPlayerResponse> findAllPlayerTeamsByEvent(Long eventId) {
        List<RelTeamPlayer> relTeamPlayerList = teamPlayerRepository.findByTeam_Event_Id(eventId);
        if (relTeamPlayerList.isEmpty()) throw new CrudServiceException(this.getClass().getName(), CrudOperationType.READ, eventId);

        return teamPlayerMapper.toResponseList(relTeamPlayerList);
    }
}
