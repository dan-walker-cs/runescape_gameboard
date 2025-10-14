package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.service.dto.EventTeamPlayerDTO;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerScoreResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Helper class to map the RelEventPlayer relationship entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class RelEventPlayerMapper {

    /**
     * Maps a list of EventTeamPlayerDTOs into a list of PlayerScoreResponse objects for the UI.
     * @param eventTeamPlayerDTOList
     * @return List<PlayerScoreResponse>
     */
    public List<PlayerScoreResponse> toPlayerScoreResponseList(List<EventTeamPlayerDTO> eventTeamPlayerDTOList) {
        return eventTeamPlayerDTOList
            .stream()
            .map(this::toPlayerScoreResponse)
            .toList();
    }

    /**
     * Maps a single EventTeamPlayerDTO into a PlayerScoreResponse object for the UI.
     * @param eventTeamPlayerDTO
     * @return PlayerScoreResponse
     */
    public PlayerScoreResponse toPlayerScoreResponse(EventTeamPlayerDTO eventTeamPlayerDTO) {
        return PlayerScoreResponse.builder()
            .teamId(eventTeamPlayerDTO.teamId())
            .teamName(eventTeamPlayerDTO.teamName())
            .playerId(eventTeamPlayerDTO.playerId())
            .playerName(eventTeamPlayerDTO.playerName())
            .score(eventTeamPlayerDTO.score())
            .build();
    }
}
