package com.nastyhaze.homeworld.hwe_app.service.dto;

import lombok.Builder;

/**
 *  DTO containing Tile-Coordinate relationship data.
 */
@Builder
public record GridTile (
    Long tileId,
    int qCoord,
    int rCoord,
    int sCoord
) {}
