package com.nastyhaze.homeworld.hwe_app.domain.data;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Entity to represent Team data.
 */
@Entity
@Table(name = "team")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Team extends AuditEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;

    @Column(nullable = false, length = 64)
    String name;
}
