package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.repository.TileRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.TileMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.TileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 *  Service layer between TileController & TileRepository.
 *  Provides logic to retrieve and mutate repository data for the API.
 *  This layer is BLOCKING & NON-REACTIVE.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TileService {

    private final TileRepository tileRepository;
    private final TileMapper tileMapper;

    public List<TileResponse> findAllTiles() {
        return tileRepository.findAll()
            .stream()
            .map(tileMapper::toResponse)
            .toList();
    }
}
