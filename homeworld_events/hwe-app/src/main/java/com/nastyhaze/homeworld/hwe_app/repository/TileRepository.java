package com.nastyhaze.homeworld.hwe_app.repository;

import com.nastyhaze.homeworld.hwe_app.domain.Tile;
import org.springframework.data.jpa.repository.*;

/**
 *  Repository class to retrieve Tile entity data.
 */
public interface TileRepository extends JpaRepository<Tile, Long> {

}