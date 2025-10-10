package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 * Response object containing Board entity fields for display via UI.
 * @param id
 * @param widthQ
 * @param heightR
 */
@Builder
public record BoardResponse(
    Long id,
    int widthQ,
    int heightR
) {}
