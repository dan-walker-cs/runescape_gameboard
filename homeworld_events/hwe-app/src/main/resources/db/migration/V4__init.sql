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
    team_name     VARCHAR(64) NOT NULL DEFAULT 'FREE_AGENT',
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

CREATE TABLE IF NOT EXISTS rel_event_tile (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id      BIGINT NOT NULL,
    tile_id       BIGINT NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_rel_event_tile_event_id FOREIGN KEY (event_id) REFERENCES event(id),
    CONSTRAINT fk_rel_event_tile_tile_id FOREIGN KEY (tile_id) REFERENCES tile(id),
    UNIQUE KEY uq_rel_event_tile_event_tile (event_id, tile_id),
    KEY ix_rel_event (event_id),
    KEY ix_rel_tile (tile_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Populate default Event for fail-over
INSERT INTO event (title,start_dt,end_dt,buy_in,rules_path,active)
    VALUES ('DEFAULT_EVENT_FALLBACK',now(),now(),'2.47b','assets/templates/error.html',0);

-- Populate tables with Winter 2025 data

INSERT INTO event (title,start_dt,end_dt,buy_in,rules_path)
    VALUES ('Winter 2025 HexScape','2025-11-07 10:00:00','2025-11-23 23:59:00','1.5 bonds per Player','assets/templates/2025_winter_rules.html');

INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 1, 'TEAM_ONE');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 2, 'TEAM_ONE');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 3, 'TEAM_ONE');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 4, 'TEAM_ONE');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 5, 'TEAM_ONE');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 6, 'TEAM_TWO');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 7, 'TEAM_TWO');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 8, 'TEAM_TWO');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 9, 'TEAM_TWO');
INSERT INTO rel_event_player (event_id, player_id, team_name)
    VALUES (1, 10, 'TEAM_TWO');

INSERT INTO rel_event_tile (event_id, tile_id)
    SELECT 1, id
    FROM tile
    WHERE id <= 83;
