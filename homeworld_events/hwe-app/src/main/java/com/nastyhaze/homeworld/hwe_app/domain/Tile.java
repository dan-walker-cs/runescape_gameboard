package com.nastyhaze.homeworld.hwe_app.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 *  Entity to represent Tile data.
 */
@Entity
@Table(name = "tile")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Tile extends AuditEntity {

    @Column(nullable = false, length = 128)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int weight;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserved_by")
    @ToString.Exclude
    private Player reservedBy;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_by")
    @ToString.Exclude
    private Player completedBy;

    @Column(name = "icon_path", nullable = false)
    private String iconPath;
}