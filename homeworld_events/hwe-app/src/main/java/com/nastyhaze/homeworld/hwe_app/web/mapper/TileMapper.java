package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.data.Tile;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import org.springframework.stereotype.Component;

/**
 *  Helper class to map the Tile entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class TileMapper {

    /**
     * Maps a Tile entity object to a TileResponse object.
     * @param tileEntity
     * @return TileResponse
     */
    public TileResponse toResponse(Tile tileEntity) {
        return TileResponse.builder()
            .id(tileEntity.getId())
            .title(tileEntity.getTitle())
            .description(tileEntity.getDescription())
            .weight(tileEntity.getWeight())
            .build();
    }
}