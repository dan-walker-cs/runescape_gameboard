package com.nastyhaze.homeworld.hwe_app.web.request;

import lombok.Builder;

/**
 * Request object containing Tile entity fields for update via Backend.
 * @param isReserved
 * @param reservedBy
 * @param isCompleted
 * @param completedBy
 */
@Builder
public record TileUpdateRequest (
    boolean isReserved,
    String reservedBy,
    boolean isCompleted,
    String completedBy
) {}
