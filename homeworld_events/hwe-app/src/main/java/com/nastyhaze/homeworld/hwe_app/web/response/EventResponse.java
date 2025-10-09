package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;
import java.time.Instant;

/**
 * Response object containing Event entity fields for display via UI.
 * @param title
 * @param startDt
 * @param endDt
 * @param buyIn
 * @param rulesPath
 */
@Builder
public record EventResponse (
    Long id,
    String title,
    Instant startDt,
    Instant endDt,
    String buyIn,
    String rulesPath
) {}
