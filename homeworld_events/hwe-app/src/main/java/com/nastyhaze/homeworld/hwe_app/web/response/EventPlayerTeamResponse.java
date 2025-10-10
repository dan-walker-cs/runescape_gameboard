package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

/**
 * Response object containing Event-Player relationship fields for display via UI.
 * @param eventId
 * @param playerNamesByTeam
 */
@Builder
public record EventPlayerTeamResponse(
    Long eventId,
    Map<String, List<String>> playerNamesByTeam
) {}
