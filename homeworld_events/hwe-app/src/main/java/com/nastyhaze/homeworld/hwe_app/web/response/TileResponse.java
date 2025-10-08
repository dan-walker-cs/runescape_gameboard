package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 * Response object containing Tile entity fields for display via UI.
 * @param id
 * @param title
 * @param description
 * @param weight
 * @param isReserved
 * @param reservedBy
 * @param isCompleted
 * @param completedBy
 * @param iconPath
 */
@Builder
public record TileResponse (
    Long id,
    String title,
    String description,
    int weight,
    boolean isReserved,
    String reservedBy,
    boolean isCompleted,
    String completedBy,
    String iconPath
) {}
