package com.nastyhaze.homeworld.hwe_app.service.dto;

import lombok.Builder;

/**
 * DTO containing Tile-Coordinate relationship data.
 * @param tileId
 * @param qCoord
 * @param rCoord
 * @param sCoord
 */
@Builder
public record GridTileDTO(
    Long tileId,
    int qCoord,
    int rCoord,
    int sCoord
) {}
