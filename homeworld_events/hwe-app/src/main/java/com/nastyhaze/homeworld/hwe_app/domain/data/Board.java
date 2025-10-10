package com.nastyhaze.homeworld.hwe_app.domain.data;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Entity to represent Board data.
 */
@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Board extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;

    @Column(name = "width_q", nullable = false)
    int widthQ;

    @Column(name = "height_r", nullable = false)
    int heightR;
}
