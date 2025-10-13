package com.nastyhaze.homeworld.hwe_app.web.response;

import com.nastyhaze.homeworld.hwe_app.service.dto.GridTileDTO;
import lombok.Builder;

import java.util.List;

/**
 * Response object containing Board-Tile relationship fields for display via UI.
 * @param boardId
 * @param gridTileList
 */
@Builder
public record BoardTileResponse(
    Long boardId,
    List<GridTileDTO> gridTileList
) {}
