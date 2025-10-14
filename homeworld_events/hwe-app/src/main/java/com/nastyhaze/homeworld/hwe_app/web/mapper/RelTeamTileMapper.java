package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamTile;
import com.nastyhaze.homeworld.hwe_app.exception.MapperSourceException;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamTileResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Helper class to map the RelTeamTile relationship entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class RelTeamTileMapper {

    /**
     * Maps a list of RelTeamTile entities into a list of TeamTileResponse objects for the UI.
     * @param relEntityList
     * @return List<TeamTileResponse>
     */
    public List<TeamTileResponse> toResponseList(List<RelTeamTile> relEntityList) {
        if (relEntityList.isEmpty()) throw new MapperSourceException(this.getClass().getName());

        return relEntityList
            .stream()
            .map(this::toResponse)
            .toList();
    }

    /**
     * Maps a single RelTeamTile entity into a TeamTileResponse object for the UI.
     * @param relEntity
     * @return TeamTileResponse
     */
    public TeamTileResponse toResponse(RelTeamTile relEntity) {
        return TeamTileResponse.builder()
            .relId(relEntity.getId())
            .teamId(relEntity.getTeam().getId())
            .teamName(relEntity.getTeam().getName())
            .tileId(relEntity.getTile().getId())
            .title(relEntity.getTile().getTitle())
            .description(relEntity.getTile().getDescription())
            .weight(relEntity.getTile().getWeight())
            .isReserved(relEntity.getIsReserved())
            .reservedBy(relEntity.getIsReserved() ? relEntity.getReservedBy().getDisplayName() : null)
            .isCompleted(relEntity.getIsCompleted())
            .completedBy(relEntity.getIsCompleted() ? relEntity.getCompletedBy().getDisplayName() : null)
            .iconPath(relEntity.getTile().getIconPath())
            .build();
    }
}
