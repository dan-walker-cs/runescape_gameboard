package com.nastyhaze.homeworld.hwe_app.repository.rel;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamTile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Repository class to retrieve Team-Tile relational data.
 */
@Repository
public interface RelTeamTileRepository extends JpaRepository<RelTeamTile, Long> {

    /**
     * Retrieves all RelTeamTile entities associated with the given teamId.
     * @param teamId
     * @return List<RelTeamTile>
     */
    @EntityGraph(attributePaths = {"team", "tile", "reservedBy", "completedBy"})
    List<RelTeamTile> findAllByTeamIdAndActiveTrue(Long teamId);
}
