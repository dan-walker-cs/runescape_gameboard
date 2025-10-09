package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 * Response object containing Player entity fields for display via UI.
 * @param id
 * @param displayName
 */
@Builder
public record PlayerResponse (
    Long id,
    String displayName
) {}
