package com.nastyhaze.homeworld.hwe_app.service.rel;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;
import com.nastyhaze.homeworld.hwe_app.domain.rel.RelBoardTile;
import com.nastyhaze.homeworld.hwe_app.exception.CrudServiceException;
import com.nastyhaze.homeworld.hwe_app.repository.rel.RelBoardTileRepository;
import com.nastyhaze.homeworld.hwe_app.web.mapper.RelBoardTileMapper;
import com.nastyhaze.homeworld.hwe_app.web.response.BoardTileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 *  Service layer between RelBoardTile Controller & Repository.
 *  Provides logic to retrieve and mutate repository Board & Tile relationship data for the UI.
 */
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelBoardTileService {

    private final RelBoardTileRepository boardTileRepository;
    private final RelBoardTileMapper boardTileMapper;

    /**
     * Returns a BoardTileResponse object containing:
     *      - Board id
     *      - list of tileId & coordinate DTOs (GridTile)
     * @param boardId
     * @return
     */
    public BoardTileResponse getTilesByBoardId(Long boardId) {
        List<RelBoardTile> relBoardTileList = boardTileRepository.findAllByBoardIdAndActiveTrue(boardId);
        if(relBoardTileList.isEmpty()) throw new CrudServiceException(this.getClass().getName(), CrudOperationType.READ, boardId);

        return boardTileMapper.toResponse(relBoardTileList);
    }
}
