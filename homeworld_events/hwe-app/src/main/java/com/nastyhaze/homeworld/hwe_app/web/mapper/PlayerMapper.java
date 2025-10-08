package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.Player;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerResponse;
import org.springframework.stereotype.Component;

/**
 *  Helper class to map the Player entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class PlayerMapper {

    /**
     * Maps a Player entity object to a PlayerResponse object.
     * @param playerEntity
     * @return PlayerResponse
     */
    public PlayerResponse toResponse(Player playerEntity) {
        return PlayerResponse.builder()
            .displayName(playerEntity.getDisplayName())
            .build();
    }
}
