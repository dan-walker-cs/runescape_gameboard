package com.nastyhaze.homeworld.hwe_app.domain.data;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
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

    @Column(name = "icon_path", nullable = false)
    private String iconPath;
}