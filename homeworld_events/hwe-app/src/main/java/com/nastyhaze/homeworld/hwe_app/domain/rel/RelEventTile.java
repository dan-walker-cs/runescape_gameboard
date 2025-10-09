package com.nastyhaze.homeworld.hwe_app.domain.rel;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.data.Event;
import com.nastyhaze.homeworld.hwe_app.domain.data.Tile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rel_event_tile")
@Getter
@Setter
@ToString(exclude = {"event", "tile"})
public class RelEventTile extends AuditEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_id", nullable = false)
    private Tile tile;
}
