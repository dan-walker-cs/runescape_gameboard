# runescape_gameboard

# Tech Stack
- HTML/CSS > Typescript > Java 17 > MySQL 8
- CSS Framework: none
- Frontend Framework: Angular 17
- Backend Framework: Spring/SpringBoot

# Git Practices
- Contributors should follow the Git naming conventions below to have their PRs approved
- Primary Branch: main
- Release Branches: vx.x (i.e. v0.1)
- Feature Branches: feature/vx.x/feature_name (i.e. feature/v0.1/overview_template)

# General Requirements
- Display all event overview and rules data
- Display all team & member data
- Provide interactive tileset
    - Tiles show icon related to objective they represent
    - Tiles are selectable
        - When a tile is selected, the tile is highlighted yellow
        - When a tile is selected, a dialog window appears to display tile data
            - Tile data includes objective name, point value, objective description
    - Tiles can be marked as reserved so users can indicate intent to complete
        - When a tile is reserved, the tile is highlighted blue
        - When a tile is reserved, the user reserving the tile is displayed
    - Tiles can be marked as completed so users can indicate progress on the gameboard
        - When a tile is completed, the tile is highlighted green
        - When a tile is completed, the user who completed the tile is displayed
        - When a tile is completed, it can no longer be marked as reserved & any reservation info is cleared
    - Support multiple users with live, stateful updates; concurrent & asynchronous operations retain data integrity
- Provide read-only tileset

# Version Descriptions / Development Roadmap
- v0.1: HTML & CSS templating
- v0.2: Angular integration, routing, components, & basic game-tile functionality
- v0.3: Angular services, dependency injection, and refactoring
- v0.4: SpringBoot (backend) boilerplate and integration
- v0.5: MySQL database creation and integration
- v0.6: Tile Update database persistence, player validation, tile event enums, basic refactoring
- v0.7: Event, RelTileEvent w/ grid pos, RelPlayerEvent w/ scoring & team, custom backend exceptions **<-- Current**
- v0.8: Alpha interactive Gameboard implementation: grid, team coloring, tile validity
- v0.9: AWS Infrastructure, CI/CD, remote compatability
- v1.0: Alpha single-user compatability release (self)
- v1.1: Implement Rules webpage & navigation
- v1.2: Implement Tiles webpage & navigation
- v1.3: User Acceptance Testing & bugfixes
- v2.0: Beta single-user compatability release (other)
- v2.x: Asychronous & concurrent multi-user compatability implementation
- v3.0: Alpha multi-user compatability release (Event Release)