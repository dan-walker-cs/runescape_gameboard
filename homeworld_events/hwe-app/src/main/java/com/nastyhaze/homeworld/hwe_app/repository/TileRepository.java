package com.nastyhaze.homeworld.hwe_app.repository;

import com.nastyhaze.homeworld.hwe_app.domain.data.Tile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Repository class to retrieve Tile entity data.
 */
@Repository
public interface TileRepository extends JpaRepository<Tile, Long> {

    @EntityGraph(attributePaths = {"reservedBy", "completedBy"})
    List<Tile> findAllByActiveTrue();
}