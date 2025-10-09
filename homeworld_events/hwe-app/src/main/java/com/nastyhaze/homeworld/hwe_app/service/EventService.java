package com.nastyhaze.homeworld.hwe_app.service;

import com.nastyhaze.homeworld.hwe_app.constant.CommonConstant;
import com.nastyhaze.homeworld.hwe_app.domain.data.Event;
import com.nastyhaze.homeworld.hwe_app.repository.EventRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.EventMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


/**
 *  Service layer between Event Controller & EventRepository.
 *  Provides logic to retrieve and mutate repository data for the API - both for Event entities and relationships.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    /**
     * Provides the active Event as an EventResponse object.
     * @return Event
     */
    public EventResponse getCurrentEvent() {
        Event currentEvent;
        try {
            currentEvent = eventRepository.findByActiveTrue().orElseGet(this::provideDefaultEvent);
        } catch (Exception e) {
            currentEvent = provideDefaultEvent();
        }

        return eventMapper.toResponse(currentEvent);
    }

    /**
     * Fetches the default fallback Event entity data.
     * @return Event
     */
    private Event provideDefaultEvent() {
        return eventRepository.findById(CommonConstant.HIGHLANDER_LONG)
            .orElseThrow(() -> new RuntimeException("THERE IS ALWAYS SUPPOSED TO BE ONE!")); // TODO: Replace with custom EventServiceException
    }
}
