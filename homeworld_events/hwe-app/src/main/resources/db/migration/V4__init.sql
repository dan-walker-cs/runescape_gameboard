-- Event Database logic
CREATE TABLE IF NOT EXISTS event (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(128) NOT NULL,
    start_dt      TIMESTAMP NOT NULL,
    end_dt        TIMESTAMP NOT NULL,
    buy_in        VARCHAR(128) NOT NULL,
    rules_path    TEXT NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS rel_event_player (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id      BIGINT NOT NULL,
    player_id     BIGINT NOT NULL,
    score         INT NOT NULL DEFAULT 0,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_rel_event_player_event_id FOREIGN KEY (event_id) REFERENCES event(id),
    CONSTRAINT fk_rel_event_player_player_id FOREIGN KEY (player_id) REFERENCES player(id),
    UNIQUE KEY uq_rel_event_player_event_player (event_id, player_id),
    KEY ix_rel_event (event_id),
    KEY ix_rel_player (player_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Populate tables with Winter 2025 data
INSERT INTO event (title,start_dt,end_dt,buy_in,rules_path)
    VALUES ('Winter 2025 HexScape','2025-11-07 15:00:00','2025-11-24 04:59:00','1.5 bonds per Player','assets/templates/2025_winter_rules.html');

INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 1);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 2);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 3);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 4);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 5);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 6);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 7);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 8);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 9);
INSERT INTO rel_event_player (event_id, player_id)
    VALUES (1, 10);
