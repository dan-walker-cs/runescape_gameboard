# runescape_gameboard

# Tech Stack
- HTML/CSS > Typescript > Java 17 > MySQL(?)
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
        - When a tile is selected, a modal window appears to display tile data
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
- v0.3: Angular services, dependency injection, and refactoring **<-- Current**
- v0.4: SpringBoot (backend) boilerplate and integration
- v0.5: ?MySQL? (database) creation and integration
- v0.6: Alpha interactive Gameboard implementation
- v1.0: Alpha single-user compatability release
- v1.x: Implement Rules webpage & navigation
- v1.x: Implement Tiles webpage & navigation
- v1.x: User Acceptance Testing & bugfixes
- v2.0: Beta single-user compatability release
- v2.x: Asychronous & concurrent multi-user compatability implementation
- v3.0: Alpha multi-user compatability release (Event Release)