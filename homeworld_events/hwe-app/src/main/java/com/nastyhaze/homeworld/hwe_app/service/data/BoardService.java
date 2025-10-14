package com.nastyhaze.homeworld.hwe_app.service.data;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.data.BoardRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.BoardMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *  Service layer between Board Controller & Repository.
 *  Provides logic to retrieve and mutate repository data for the API.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    /**
     * Returns a BoardResponse for a given eventId.
     * @param eventId
     * @return BoardResponse
     */
    public BoardResponse getBoardByEvent(Long eventId) {
        return boardRepository.findByEventIdAndActiveTrue(eventId)
            .map(boardMapper::toResponse)
            .orElseThrow(() -> new CrudServiceException(this.getClass().getName(), CrudOperationType.READ, eventId));
    }
}
