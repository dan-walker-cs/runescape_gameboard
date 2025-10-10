package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.data.Board;
import com.nastyhaze.homeworld.hwe_app.web.response.BoardResponse;
import org.springframework.stereotype.Component;

/**
 *  Helper class to map the Event entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class BoardMapper {

    /**
     * Maps a Board entity object to a BoardResponse object.
     * @param boardEntity
     * @return BoardResponse
     */
    public BoardResponse toResponse(Board boardEntity) {
        return BoardResponse.builder()
            .id(boardEntity.getId())
            .widthQ(boardEntity.getWidthQ())
            .heightR(boardEntity.getHeightR())
            .build();
    }
}
