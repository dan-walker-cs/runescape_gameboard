package com.nastyhaze.homeworld.hwe_app.repository.rel;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelBoardTile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Repository class to retrieve Board-Tile relational data.
 */
@Repository
public interface RelBoardTileRepository extends JpaRepository<RelBoardTile, Long> {

    /**
     * Returns all Board-Tile relationships for the given boardId.
     * @param boardId
     * @return List<RelBoardTile>
     */
    List<RelBoardTile> findAllByBoardIdAndActiveTrue(Long boardId);
}
