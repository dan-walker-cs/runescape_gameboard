package com.nastyhaze.homeworld.hwe_app.domain.rel;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.data.Player;
import com.nastyhaze.homeworld.hwe_app.domain.data.Team;
import com.nastyhaze.homeworld.hwe_app.domain.data.Tile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  Entity representing Team-Tile associations & data.
 */
@Entity
@Table(name = "rel_team_tile")
@Getter
@Setter
@ToString
public class RelTeamTile extends AuditEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @ToString.Exclude
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_id", nullable = false)
    @ToString.Exclude
    private Tile tile;

    @Column(name = "is_reserved", nullable = false)
    private Boolean isReserved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserved_by")
    @ToString.Exclude
    private Player reservedBy;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_by")
    @ToString.Exclude
    private Player completedBy;
}
