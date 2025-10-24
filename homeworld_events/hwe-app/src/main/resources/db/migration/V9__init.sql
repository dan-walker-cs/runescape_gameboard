SET @icon_path_prefix = 'assets/icons/';
SET @icon_path_postfix = '_tile_icon.png';

-- Replacing the Molch Pearl tile due to tracking issues
UPDATE tile
    SET title="An Egg's Delight",
        description='Obtain either the Petal Garland, Fox Whistle, or Golden Pheasant Egg from Forestry events',
        icon_path=CONCAT(@icon_path_prefix, 'forestry', @icon_path_postfix)
    WHERE id = 32;
UPDATE tile
    SET description='PRE-REQ: none. Receive the Golden Tench from Aerial Fishing'
    WHERE id = 80;

-- Fix the Yama tile name
UPDATE tile
    SET title='Contracting Worms',
        description='Receive the Soulflame Horn or any Oathplate piece from Yama',
        icon_path=CONCAT(@icon_path_prefix, 'yama', @icon_path_postfix)
    WHERE id = 65;