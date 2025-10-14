package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.data.Team;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamResponse;
import org.springframework.stereotype.Component;

/**
 *  Helper class to map the Team entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class TeamMapper {

    /**
     * Maps a Player entity object to a PlayerResponse object.
     * @param teamEntity
     * @return TeamResponse
     */
    public TeamResponse toResponse(Team teamEntity) {
        return TeamResponse.builder()
            .id(teamEntity.getId())
            .name(teamEntity.getName())
            .build();
    }
}
