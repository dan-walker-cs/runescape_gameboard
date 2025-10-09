package com.nastyhaze.homeworld.hwe_app.repository;

import com.nastyhaze.homeworld.hwe_app.domain.Tile;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 *  Repository class to retrieve Tile entity data.
 */
public interface TileRepository extends JpaRepository<Tile, Long> {

    @EntityGraph(attributePaths = {"reservedBy", "completedBy"})
    List<Tile> findAllByActiveTrue();
}