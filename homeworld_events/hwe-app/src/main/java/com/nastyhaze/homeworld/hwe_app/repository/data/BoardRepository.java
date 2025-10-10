package com.nastyhaze.homeworld.hwe_app.repository.data;

import com.nastyhaze.homeworld.hwe_app.domain.data.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  Repository class to retrieve Board entity data.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * Only 1 Board should ever be active in the repository for a given eventId.
     * @param eventId
     * @return Optional<Board>
     */
    Optional<Board> findByEventIdAndActiveTrue(Long eventId);
}
