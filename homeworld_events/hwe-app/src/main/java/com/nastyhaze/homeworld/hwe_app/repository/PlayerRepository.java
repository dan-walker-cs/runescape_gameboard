package com.nastyhaze.homeworld.hwe_app.repository;

import com.nastyhaze.homeworld.hwe_app.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  Repository class to retrieve Player entity data.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Returns a single, active Player with the given displayName - ignoring case, if one exists.
     * @param displayName
     * @return Optional<Player>
     */
    Optional<Player> findOneByDisplayNameIgnoreCaseAndActiveTrue(String displayName);
}
