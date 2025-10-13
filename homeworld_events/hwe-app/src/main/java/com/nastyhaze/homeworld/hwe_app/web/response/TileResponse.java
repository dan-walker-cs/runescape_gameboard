package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 *
 * @param id
 * @param title
 * @param description
 * @param weight
 */
@Builder
public record TileResponse (
    Long id,
    String title,
    String description,
    int weight
) {}
