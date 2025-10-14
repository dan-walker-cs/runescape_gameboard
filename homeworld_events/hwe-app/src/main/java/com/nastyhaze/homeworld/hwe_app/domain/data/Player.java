package com.nastyhaze.homeworld.hwe_app.domain.data;

import com.nastyhaze.homeworld.hwe_app.domain.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 *  Entity to represent Player data.
 */
@Entity
@Table(name = "player")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Player extends AuditEntity {
    @Column(name = "display_name", nullable = false, length = 64)
    private String displayName;
}
