package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 * Response object containing Team-Tile relationship fields for display via UI.
 * @param relId
 * @param teamId
 * @param teamName
 * @param tileId
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
public record TeamTileResponse (
    Long relId,
    Long teamId,
    String teamName,
    Long tileId,
    String title,
    String description,
    int weight,
    boolean isReserved,
    String reservedBy,
    boolean isCompleted,
    String completedBy,
    String iconPath
) {}
