package com.nastyhaze.homeworld.hwe_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 *  Entity to represent Event data.
 */
@Entity
@Table(name = "event")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Event extends AuditEntity {

    @Column(nullable = false, length = 128)
    String title;

    @Column(name = "start_dt", nullable = false)
    Instant startDt;

    @Column(name = "end_dt", nullable = false)
    Instant endDt;

    @Column(name = "buy_in", length = 128, nullable = false)
    String buyIn;

    @Column(name = "rules_path", columnDefinition = "TEXT", nullable = false)
    String rulesPath;
}
