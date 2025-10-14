package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.repository.rel.RelEventPlayerRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelEventPlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *  Service layer between RelEventPlayer Controller & EventRepository.
 *  Provides logic to retrieve and mutate repository Event-Player relationship data for the UI.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelEventPlayerService {

    private final RelEventPlayerRepository eventPlayerRepository;
    private final RelEventPlayerMapper eventPlayerMapper;

}
