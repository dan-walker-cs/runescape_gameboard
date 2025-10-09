package com.nastyhaze.homeworld.hwe_app.repository;

import com.nastyhaze.homeworld.hwe_app.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  Repository class to retrieve Event entity & relationship data.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Only 1 event should ever be active in the repository.
     * @return List<Event>
     */
    Optional<Event> findByActiveTrue();
}
