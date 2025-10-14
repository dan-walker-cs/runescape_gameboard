package com.nastyhaze.homeworld.hwe_app.service.data;

import com.nastyhaze.homeworld.hwe_app.repository.data.TeamRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.TeamMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 *  Service layer between Team Controller & Repository.
 *  Provides logic to retrieve and mutate repository data for the API.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    /**
     * Find all active Teams associated with eventId - trimmed to Response object.
     * @param eventId
     * @return List<TeamResponse>
     */
    public List<TeamResponse> findAllByEvent(Long eventId) {
        return teamRepository.findAllByEventIdAndActiveTrue(eventId)
            .stream()
            .map(teamMapper::toResponse)
            .toList();
    }
}
