package com.nastyhaze.homeworld.hwe_app.repository.rel;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelEventPlayer;
import com.nastyhaze.homeworld.hwe_app.service.dto.EventTeamPlayerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Repository class to retrieve Event-Player relational data.
 */
@Repository
public interface RelEventPlayerRepository extends JpaRepository<RelEventPlayer, Long> {

    @Query("""
        SELECT new com.nastyhaze.homeworld.hwe_app.service.dto.EventTeamPlayerDTO(
              e.id, t.id, t.name, p.id, p.displayName, COALESCE(rep.score, 0)
        )
        FROM RelTeamPlayer rtp
        JOIN rtp.team   t
        JOIN t.event    e
        JOIN rtp.player p
        LEFT JOIN RelEventPlayer rep ON rep.event = e AND rep.player = p
        WHERE e.id = :eventId
            AND e.active = true AND t.active = true AND p.active = true
    """)
    List<EventTeamPlayerDTO> findEventTeamPlayerByEventIdAndActiveTrue(Long eventId);
}
