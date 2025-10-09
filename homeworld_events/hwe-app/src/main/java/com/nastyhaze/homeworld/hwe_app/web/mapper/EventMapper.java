package com.nastyhaze.homeworld.hwe_app.web.mapper;

import com.nastyhaze.homeworld.hwe_app.domain.data.Event;
import com.nastyhaze.homeworld.hwe_app.web.response.EventResponse;
import org.springframework.stereotype.Component;


/**
 *  Helper class to map the Event entity to DTO, Request, and Response objects - & vice versa.
 */
@Component
public class EventMapper {

    /**
     * Maps an Event entity object to a EventResponse object.
     * @param eventEntity
     * @return EventResponse
     */
    public EventResponse toResponse(Event eventEntity) {
        return EventResponse.builder()
            .id(eventEntity.getId())
            .title(eventEntity.getTitle())
            .startDt(eventEntity.getStartDt())
            .endDt(eventEntity.getEndDt())
            .buyIn(eventEntity.getBuyIn())
            .rulesPath(eventEntity.getRulesPath())
            .build();
    }
}
