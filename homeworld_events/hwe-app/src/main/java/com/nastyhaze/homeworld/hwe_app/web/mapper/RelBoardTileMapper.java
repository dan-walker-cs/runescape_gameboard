package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelBoardTile;
import com.nastyhaze.homeworld.hwe_app.exception.MapperSourceException;
import com.nastyhaze.homeworld.hwe_app.service.dto.GridTileDTO;
import com.nastyhaze.homeworld.hwe_app.web.response.BoardTileResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Helper class to map the RelBoardTile relationship entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class RelBoardTileMapper {

    /**
     * Maps a list of RelBoardTile entity objects to a BoardTileResponse object.
     * @param relEntityList
     * @return
     */
    public BoardTileResponse toResponse(List<RelBoardTile> relEntityList) {
        Long boardId = relEntityList
            .stream()
            .findAny()
            .orElseThrow(() -> new MapperSourceException(this.getClass().getName()))
            .getBoard()
            .getId();

        List<GridTileDTO> gridTileDTOList = relEntityList
            .stream()
            .map(this::toGridTile)
            .toList();

        return BoardTileResponse.builder()
            .boardId(boardId)
            .gridTileList(gridTileDTOList)
            .build();
    }

    /**
     * Maps a RelBoardTile Entity to the GridTile DTO.
     * @param relEntity
     * @return
     */
    public GridTileDTO toGridTile(RelBoardTile relEntity) {
        return GridTileDTO.builder()
            .tileId(relEntity.getTile().getId())
            .qCoord(relEntity.getQCoord())
            .rCoord(relEntity.getRCoord())
            .sCoord(relEntity.getSCoord())
            .build();
    }

}
