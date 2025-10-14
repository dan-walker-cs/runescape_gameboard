package com.nastyhaze.homeworld.hwe_app.repository.data;

import com.nastyhaze.homeworld.hwe_app.domain.data.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Repository class to retrieve Team entity data.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     *  Find all active Teams associated with eventId.
     * @param eventId
     * @return List<Team>
     */
    List<Team> findAllByEventIdAndActiveTrue(Long eventId);
}
