package com.nastyhaze.homeworld.hwe_app.domain.rel;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.data.Event;
import com.nastyhaze.homeworld.hwe_app.domain.data.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  Entity representing Event-Player associations.
 */
@Entity
@Table(name = "rel_event_player")
@Getter
@Setter
@ToString
public class RelEventPlayer extends AuditEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @ToString.Exclude
    private Player player;

    @Column(name = "score", nullable = false)
    private int score = 0;
}
