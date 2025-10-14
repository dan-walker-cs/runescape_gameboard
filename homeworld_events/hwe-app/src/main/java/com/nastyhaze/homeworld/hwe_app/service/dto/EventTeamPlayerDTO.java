package com.nastyhaze.homeworld.hwe_app.service.dto;

import lombok.Builder;

/**
 * DTO to hold Event-Team-Player data for backend logic requirements.
 * @param eventId
 * @param teamId
 * @param teamName
 * @param playerId
 * @param playerName
 * @param score
 */
@Builder
public record EventTeamPlayerDTO (
    Long eventId,
    Long teamId,
    String teamName,
    Long playerId,
    String playerName,
    int score
) {}
