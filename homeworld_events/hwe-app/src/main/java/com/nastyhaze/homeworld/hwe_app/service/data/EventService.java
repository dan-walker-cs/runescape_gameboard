package com.nastyhaze.homeworld.hwe_app.service.data;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.domain.data.Event;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.data.EventRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.EventMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


/**
 *  Service layer between Event Controller & Repository.
 *  Provides logic to retrieve and mutate repository data for the API.
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
        Event currentEvent = eventRepository.findByActiveTrue()
            .orElseThrow(() -> new CrudServiceException(this.getClass().getName(), CrudOperationType.READ));

        return eventMapper.toResponse(currentEvent);
    }
}
