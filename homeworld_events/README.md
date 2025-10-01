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
- Provide read-only tileset

# Version Descriptions
- v0.1: HTML & CSS templating
- v0.2: Angular integration & routing
- v0.3: Basic frontend logic for components **<-- Current**
- v0.4: Angular services & dependency injection
- v0.5: ?Backend work begins?