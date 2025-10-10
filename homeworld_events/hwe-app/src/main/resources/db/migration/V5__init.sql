-- Logic for Board data
CREATE TABLE IF NOT EXISTS board (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id      BIGINT NOT NULL,
    width_q       INT NOT NULL,
    height_r      INT NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_board_event_id FOREIGN KEY (event_id) REFERENCES event(id),
    KEY ix_rel_event (event_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS rel_board_tile (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id      BIGINT NOT NULL,
    tile_id       BIGINT NOT NULL,
    q_coord       INT NOT NULL,
    r_coord       INT NOT NULL,
    s_coord       INT AS (-(q_coord + r_coord)) STORED,
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_dt    TIMESTAMP DEFAULT NULL,
    CONSTRAINT fk_rel_board_tile_board_id FOREIGN KEY (board_id) REFERENCES board(id),
    CONSTRAINT fk_rel_board_tile_tile_id FOREIGN KEY (tile_id) REFERENCES tile(id),
    KEY ix_rel_board (board_id),
    KEY ix_rel_tile (tile_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert Winter 2025 board
INSERT INTO board (event_id, width_q, height_r)
    VALUES (2, 32, 24);

-- Insert start tile
INSERT INTO rel_board_tile (board_id, tile_id, q_coord, r_coord)
    VALUES (1, 1, 0, 0);

-- Insert remaining tiles
INSERT INTO rel_board_tile (board_id, tile_id, q_coord, r_coord)
    SELECT 1, id, 1, 1 from tile;