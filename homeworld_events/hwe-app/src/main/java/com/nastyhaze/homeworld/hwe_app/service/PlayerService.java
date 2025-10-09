package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.Player;
import com.nastyhaze.homeworld.hwe_app.exception.PlayerServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.PlayerRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.PlayerMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    private final PlayerMapper playerMapper;

    public List<PlayerResponse> findAllPlayers() {
        return playerRepository.findAll()
            .stream()
            .filter(AuditEntity::isActive) // TODO: Can filter at repository level instead
            .map(playerMapper::toResponse)
            .toList();
    }

    /**
     * Returns a single, active Player entity.
     * @param displayName
     * @return Player
     */
    public Player findByDisplayName(String displayName) {
        return playerRepository.findOneByDisplayNameIgnoreCaseAndActiveTrue(displayName)
            .orElseThrow(() -> new PlayerServiceException(displayName, CrudOperationType.READ));
    }
}
