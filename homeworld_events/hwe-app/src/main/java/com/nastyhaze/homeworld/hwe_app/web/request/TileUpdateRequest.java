package com.nastyhaze.homeworld.hwe_app.web.request;

import lombok.Builder;

/**
 * Request object containing Tile entity fields for update via Backend.
 * @param eventId
 * @param isReserved
 * @param reservedBy
 * @param isCompleted
 * @param completedBy
 */
@Builder
public record TileUpdateRequest (
    Long eventId,
    boolean isReserved,
    String reservedBy,
    boolean isCompleted,
    String completedBy
) {}
