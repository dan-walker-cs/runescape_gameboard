package com.nastyhaze.homeworld.hwe_app.repository.rel;

import com.nastyhaze.homeworld.hwe_app.domain.rel.RelEventPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Repository class to retrieve Event-Player relational data.
 */
@Repository
public interface RelEventPlayerRepository extends JpaRepository<RelEventPlayer, Long> {

}
