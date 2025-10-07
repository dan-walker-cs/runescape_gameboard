package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.Tile;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import org.springframework.stereotype.Component;

/**
 *  Helper class to map the Tile entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class TileMapper {

    /**
     *
     * @param tileEntity
     * @return
     */
    public TileResponse toResponse(Tile tileEntity) {
        return TileResponse.builder()
            .title(tileEntity.getTitle())
            .description(tileEntity.getDescription())
            .weight(tileEntity.getWeight())
            .isReserved(tileEntity.isReserved())
            .reservedBy(tileEntity.isReserved() ? tileEntity.getReservedBy().getDisplayName() : null)
            .isCompleted(tileEntity.isCompleted())
            .completedBy(tileEntity.isCompleted() ? tileEntity.getCompletedBy().getDisplayName() : null)
            .iconPath(tileEntity.getIconPath())
            .build();
    }
}