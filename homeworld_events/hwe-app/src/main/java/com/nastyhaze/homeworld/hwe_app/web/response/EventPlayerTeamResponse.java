package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record EventPlayerTeamResponse(
    Long eventId,
    Map<String, List<String>> playerNamesByTeam
) {}
