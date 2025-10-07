CREATE TABLE player (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  display_name  VARCHAR(64) NOT NULL UNIQUE,
  created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_dt    TIMESTAMP DEFAULT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tile (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  title         VARCHAR(128) NOT NULL,
  description   TEXT NOT NULL,
  weight        INT NOT NULL,
  is_reserved   BOOLEAN NOT NULL DEFAULT FALSE,
  reserved_by   BIGINT DEFAULT NULL,
  is_completed  BOOLEAN NOT NULL DEFAULT FALSE,
  completed_by  BIGINT DEFAULT NULL,
  icon_path     TEXT NOT NULL,
  created_dt    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_dt    TIMESTAMP DEFAULT NULL,
  CONSTRAINT fk_tiles_reserved_by FOREIGN KEY (reserved_by) REFERENCES users(id),
  CONSTRAINT fk_tiles_completed_by FOREIGN KEY (completed_by) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE INDEX idx_tiles_reserved_by ON tiles(reserved_by);
CREATE INDEX idx_tiles_completed_by ON tiles(completed_by);