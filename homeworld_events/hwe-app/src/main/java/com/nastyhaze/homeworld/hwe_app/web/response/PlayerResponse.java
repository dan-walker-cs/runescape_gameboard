package com.nastyhaze.homeworld.hwe_app.web.response;

import lombok.Builder;

@Builder
public record PlayerResponse (
    Long id,
    String displayName
) {}
