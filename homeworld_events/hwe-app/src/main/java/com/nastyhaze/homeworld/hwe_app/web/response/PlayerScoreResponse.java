package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

/**
 *  Response object containing Event-Team-Player data associated with points and scoring.
 */
@Builder
public record PlayerScoreResponse(
    Long teamId,
    String teamName,
    Long playerId,
    String playerName,
    int score
) {}
