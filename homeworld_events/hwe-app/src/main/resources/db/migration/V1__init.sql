-- Basic table setup

CREATE TABLE IF NOT EXISTS player (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  display_name  VARCHAR(64) NOT NULL UNIQUE,
  active        BOOLEAN NOT NULL DEFAULT TRUE,
  created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_dt    TIMESTAMP DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS tile (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  title         VARCHAR(128) NOT NULL,
  description   TEXT NOT NULL,
  weight        INT NOT NULL,
  is_reserved   BOOLEAN NOT NULL DEFAULT FALSE,
  reserved_by   BIGINT DEFAULT NULL,
  is_completed  BOOLEAN NOT NULL DEFAULT FALSE,
  completed_by  BIGINT DEFAULT NULL,
  icon_path     TEXT NOT NULL,
  active        BOOLEAN NOT NULL DEFAULT TRUE,
  created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_dt    TIMESTAMP DEFAULT NULL,
  CONSTRAINT fk_tile_reserved_by FOREIGN KEY (reserved_by) REFERENCES player(id),
  CONSTRAINT fk_tile_completed_by FOREIGN KEY (completed_by) REFERENCES player(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE INDEX idx_tile_reserved_by ON tile(reserved_by);
CREATE INDEX idx_tile_completed_by ON tile(completed_by);