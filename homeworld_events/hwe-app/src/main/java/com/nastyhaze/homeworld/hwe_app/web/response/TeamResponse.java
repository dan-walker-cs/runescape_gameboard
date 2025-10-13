package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 * Response object containing Team entity fields for display via UI.
 * @param id
 * @param name
 */
@Builder
public record TeamResponse (
    Long id,
    String name
) {}
