package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelEventPlayer;
import com.nastyhaze.homeworld.hwe_app.exception.MapperSourceException;
import com.nastyhaze.homeworld.hwe_app.web.response.EventPlayerTeamResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Helper class to map the RelEventPlayer relationship entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class RelEventPlayerMapper {

    /**
     * Maps a list of RelEventPlayer entity objects to an EventPlayerTeamResponse object.
     * @param relEntityList
     * @return EventPlayerTeamResponse
     */
    public EventPlayerTeamResponse toEventPlayerTeamResponse(List<RelEventPlayer> relEntityList) {
        Long eventId = relEntityList
            .stream()
            .findAny()
            .orElseThrow(() -> new MapperSourceException(this.getClass().getName()))
            .getEvent()
            .getId();

        Map<String, List<String>> playersByTeam = relEntityList
            .stream()
            .collect(Collectors.groupingBy(
                RelEventPlayer::getTeamName,
                Collectors.mapping(rel -> rel.getPlayer().getDisplayName(), Collectors.toList())
            ));

        return EventPlayerTeamResponse.builder()
            .eventId(eventId)
            .playerNamesByTeam(playersByTeam)
            .build();
    }
}
