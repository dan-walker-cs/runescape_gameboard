package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

import java.util.List;

/**
 * Response object containing Team-Player relationship fields for display via UI.
 * @param teamId
 * @param playerNames
 */
@Builder
public record TeamPlayerResponse(
    Long teamId,
    List<String> playerNames
) {}
