-- Database updates for multi-team event support
CREATE TABLE IF NOT EXISTS team (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id      BIGINT NOT NULL,
    name          VARCHAR(64) NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_team_event_id FOREIGN KEY (event_id) REFERENCES event(id),
    KEY ix_rel_event (event_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS rel_team_player (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id       BIGINT NOT NULL,
    player_id     BIGINT NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_rel_team_player_team_id FOREIGN KEY (team_id) REFERENCES team(id),
    CONSTRAINT fk_rel_team_player_player_id FOREIGN KEY (player_id) REFERENCES player(id),
    KEY ix_rel_team (team_id),
    KEY ix_rel_player (player_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS rel_team_tile (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id       BIGINT NOT NULL,
    tile_id       BIGINT NOT NULL,
    is_reserved   BOOLEAN NOT NULL DEFAULT FALSE,
    reserved_by   BIGINT NOT NULL,
    is_completed  BOOLEAN NOT NULL DEFAULT FALSE,
    completed_by  BIGINT NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_rel_team_tile_reserved_by FOREIGN KEY (reserved_by) REFERENCES player(id),
    CONSTRAINT fk_rel_team_tile_completed_by FOREIGN KEY (completed_by) REFERENCES player(id),
    KEY ix_rel_team_tile_reserved_by (reserved_by),
    KEY ix_rel_team_tile_completed_by (completed_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
