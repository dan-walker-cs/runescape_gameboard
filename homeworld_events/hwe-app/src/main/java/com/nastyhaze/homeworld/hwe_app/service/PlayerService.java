package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.domain.Player;
import com.nastyhaze.homeworld.hwe_app.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *  Service layer between PlayerController & PlayerRepository.
 *  Provides logic to retrieve and mutate repository data for the API.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    /**
     * Returns a single, active Player entity.
     * @param displayName
     * @return Player
     */
    public Player getByDisplayName(String displayName) {
        return playerRepository.findOneByDisplayNameIgnoreCaseAndActiveTrue(displayName)
            .orElseThrow(); // TODO: Create custom exception class for PlayerReadException
    }
}
