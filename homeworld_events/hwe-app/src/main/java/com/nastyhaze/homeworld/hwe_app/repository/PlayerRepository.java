package com.nastyhaze.homeworld.hwe_app.repository;

import com.nastyhaze.homeworld.hwe_app.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository class to retrieve Player entity data.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
