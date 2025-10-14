package com.nastyhaze.homeworld.hwe_app.service.data;

import com.nastyhaze.homeworld.hwe_app.repository.data.TileRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.TileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *  Service layer between Tile Controller & Repository.
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

}
