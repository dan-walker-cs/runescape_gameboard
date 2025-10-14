package com.nastyhaze.homeworld.hwe_app.repository.rel;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelTeamPlayer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Repository class to retrieve Team-Player relational data.
 */
@Repository
public interface RelTeamPlayerRepository extends JpaRepository<RelTeamPlayer, Long> {

    @EntityGraph(attributePaths = {"player", "team"})
    List<RelTeamPlayer> findByTeam_Event_Id(Long eventId);
}
