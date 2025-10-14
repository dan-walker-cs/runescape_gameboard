package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.data.Team;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamPlayer;
import com.nastyhaze.homeworld.hwe_app.exception.MapperSourceException;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamPlayerResponse;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Helper class to map the RelTeamPlayer relationship entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class RelTeamPlayerMapper {

    /**
     * Maps a list of RelTeamPlayer entities into a lsit of TeamPlayerResponse objects for the UI.
     * TODO: Could be refactored to attempt O(2n) -> O(n), but I think the only way is repo query adjustment.
     * @param relEntityList
     * @return List<TeamPlayerResponse>
     */
    public List<TeamPlayerResponse> toResponseList(List<RelTeamPlayer> relEntityList) {
        if (relEntityList.isEmpty()) throw new MapperSourceException(this.getClass().getName());

        Map<Long,String> teamNamesById = relEntityList
            .stream()
            .map(RelTeamPlayer::getTeam)
            .distinct()
            .collect(Collectors.toMap(AuditEntity::getId, Team::getName) );

        return relEntityList
            .stream()
            .collect(Collectors.groupingBy(
                rtp -> rtp.getTeam().getId(),
                Collectors.mapping(rtp -> rtp.getPlayer().getDisplayName(), Collectors.toList())
            ))
            .entrySet()
            .stream()
            .map(entry -> toResponse(entry.getKey(), teamNamesById.get(entry.getKey()), entry.getValue()))
            .toList();
    }

    /**
     * Private Helper method.
     * Does not transform an Entity, just used for cleaner code.
     * @param teamId
     * @param teamName
     * @param playerNames
     * @return TeamPlayerResponse
     */
    private TeamPlayerResponse toResponse(Long teamId, String teamName, List<String> playerNames) {
        return TeamPlayerResponse.builder()
            .teamId(teamId)
            .teamName(teamName)
            .playerNames(playerNames)
            .build();
    }
}
