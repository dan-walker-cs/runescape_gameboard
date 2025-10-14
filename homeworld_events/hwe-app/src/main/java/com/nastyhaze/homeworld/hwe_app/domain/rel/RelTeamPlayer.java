package com.nastyhaze.homeworld.hwe_app.domain.rel;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.data.Player;
import com.nastyhaze.homeworld.hwe_app.domain.data.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  Entity representing Team-Player associations.
 */
@Entity
@Table(name = "rel_team_player")
@Getter
@Setter
@ToString
public class RelTeamPlayer extends AuditEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @ToString.Exclude
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @ToString.Exclude
    private Player player;
}
