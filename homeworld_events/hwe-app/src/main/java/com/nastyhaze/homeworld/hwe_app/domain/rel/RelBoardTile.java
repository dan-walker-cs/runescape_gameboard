package com.nastyhaze.homeworld.hwe_app.domain.rel;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import com.nastyhaze.homeworld.hwe_app.domain.data.Board;
import com.nastyhaze.homeworld.hwe_app.domain.data.Tile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rel_board_tile")
@Getter
@Setter
@ToString
public class RelBoardTile extends AuditEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    @ToString.Exclude
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_id", nullable = false)
    @ToString.Exclude
    private Tile tile;

    @Column(name = "q_coord", nullable = false)
    int qCoord;

    @Column(name = "r_coord", nullable = false)
    int rCoord;

    @Column(name = "s_coord", nullable = false)
    int sCoord;
}
