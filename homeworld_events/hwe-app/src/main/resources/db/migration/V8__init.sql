-- Updating tile information [board refactor]
SET @icon_path_prefix = 'assets/icons/';
SET @icon_path_postfix = '_tile_icon.png';

UPDATE tile
    SET title='I Will Use Your Helmet As A Helmet',
        description='Receive the Dragon Fullhelm from offering Chewed Bones',
        icon_path=CONCAT(@icon_path_prefix, 'dragon_fullhelm', @icon_path_postfix)
    WHERE id = 60;
UPDATE tile
    SET title='Something Something Invader Zim',
        description='Receive the Avernic Treads, Eye of Ayak, or Mokhaiotl Cloth from the Doom of Mokhaiotl',
        icon_path=CONCAT(@icon_path_prefix, 'doom_mokhaiotl', @icon_path_postfix)
    WHERE id = 61;
UPDATE tile
    SET title="Korasi's Lament",
        description='Collect all 3 pieces of Voidwaker from the Wilderness bosses',
        icon_path=CONCAT(@icon_path_prefix, 'voidwaker_complete', @icon_path_postfix)
    WHERE id = 62;
UPDATE tile
    SET title='Maybe Nex Time',
        description='Receive any non-shard unique from Nex in the God Wars Dungeon',
        icon_path=CONCAT(@icon_path_prefix, 'nex_gwd', @icon_path_postfix)
    WHERE id = 63;
UPDATE tile
    SET title='Future Clue Step',
        description='Receive every piece of either the Light or Heavy Ballista from Demonic Gorillas',
        icon_path=CONCAT(@icon_path_prefix, 'light_heavy_ballista', @icon_path_postfix)
    WHERE id = 64;
UPDATE tile
    SET title='Become Horny (or Oathy)',
        description='Receive the Soulflame Horn or any Oathplate piece from Yama',
        icon_path=CONCAT(@icon_path_prefix, 'yama', @icon_path_postfix)
    WHERE id = 65;
UPDATE tile
    SET title='Do Elves Skill?',
        description='Receive a Crystal Tool Seed or Zalcano Shard from Zalcano',
        icon_path=CONCAT(@icon_path_prefix, 'tool_seed', @icon_path_postfix)
    WHERE id = 66;
UPDATE tile
    SET title='I Thought It Was 5',
        description='Receive a "full" Venator Bow (3 shards) from Phantom Muspah',
        icon_path=CONCAT(@icon_path_prefix, 'muspah_venator_full', @icon_path_postfix)
    WHERE id = 67;
UPDATE tile
    SET title='Bring Me to Life (Wake Me Up Inside)',
        description="Receive any unique from The Nightmare or Phosani's Nightmare - other than tablet",
        icon_path=CONCAT(@icon_path_prefix, 'nightmare', @icon_path_postfix)
    WHERE id = 68;
UPDATE tile
    SET title='You Feared the Reaper (because no scythe)',
        description='Receive any non-mega-rare purple chest unique from the Theatre of Blood',
        icon_path=CONCAT(@icon_path_prefix, 'tob_normal', @icon_path_postfix)
    WHERE id = 69;
UPDATE tile
    SET title='Red Ham and Eggs',
        description='Receive the Dragon Warhammer from Lizardman Shamans',
        icon_path=CONCAT(@icon_path_prefix, 'dragon_warhammer', @icon_path_postfix)
    WHERE id = 70;
UPDATE tile
    SET title='Malodorous Masterpiece',
        description='Receive all 3 pieces of either the Odium or Malediction Ward from Wilderness demi-bosses',
        icon_path=CONCAT(@icon_path_prefix, 'odium_malediction_complete', @icon_path_postfix)
    WHERE id = 71;
UPDATE tile
    SET title='Beyond the Shadow of a Doubt',
        description='PRE-REQ: Unlock any ToA tile. Receive any mega-rare purple chest unique from the Tombs of Amascut',
        icon_path=CONCAT(@icon_path_prefix, 'toa_mega', @icon_path_postfix)
    WHERE id = 72;
UPDATE tile
    SET title='Get It Twisted',
        description='PRE-REQ: Unlock any CoX tile. Receive any mega-rare purple chest unique from the Chambers of Xeric',
        icon_path=CONCAT(@icon_path_prefix, 'cox_mega', @icon_path_postfix)
    WHERE id = 73;
UPDATE tile
    SET title='A Scythe of Relief',
        description='PRE-REQ: Unlock any ToB tile. Receive any mega-rare purple chest unique from the Theatre of Blood',
        icon_path=CONCAT(@icon_path_prefix, 'tob_mega', @icon_path_postfix)
    WHERE id = 74;
UPDATE tile
    SET title='See Examine Text',
        description='PRE-REQ: Unlock any Sepulchre tile. Loot a Ring of Endurance from the Hallowed Sepulchre',
        icon_path=CONCAT(@icon_path_prefix, 'endurance_ring', @icon_path_postfix)
    WHERE id = 75;
UPDATE tile
    SET title='Ax-ing for a Friend',
        description='PRE-REQ: Unlock any DT2 tile. Obtain every component of the Soul Reaper Axe from the Zarosian Generals',
        icon_path=CONCAT(@icon_path_prefix, 'soul_reaper_axe', @icon_path_postfix)
    WHERE id = 76;
UPDATE tile
    SET title='Smaller Version of the Thing You Killed',
        description='PRE-REQ: none. Receive any boss pet (no chompy chick)',
        icon_path=CONCAT(@icon_path_prefix, 'pet_bossing', @icon_path_postfix)
    WHERE id = 77;
UPDATE tile
    SET title='DoubleUQ & Tenzo 4eva',
        description='PRE-REQ: Unlock any DT2 tile. Obtain any Ring Vestige from the Zarosian Generals - plus 3 chromium ingots',
        icon_path=CONCAT(@icon_path_prefix, 'wedding_ring', @icon_path_postfix)
    WHERE id = 78;
UPDATE tile
    SET title='Prisonbreak!',
        description='PRE-REQ: Unlock any Gauntlet tile. Receive an Enhanced Crystal Weapon Seed from the Gauntlet - either difficulty',
        icon_path=CONCAT(@icon_path_prefix, 'enhanced_weapon_seed', @icon_path_postfix)
    WHERE id = 79;
UPDATE tile
    SET title="Rada's Regret",
        description='PRE-REQ: Unlock any Aerial Fishing tile. Receive the Golden Tench from Aerial Fishing',
        icon_path=CONCAT(@icon_path_prefix, 'golden_tench', @icon_path_postfix)
    WHERE id = 80;
UPDATE tile
    SET title='Do Players Skill?',
        description='PRE-REQ: none. Receive any skilling pet - skilling bosses count! (no chompy chick)',
        icon_path=CONCAT(@icon_path_prefix, 'pet_skilling', @icon_path_postfix)
    WHERE id = 81;
UPDATE tile
    SET title="Sitter's Euphoria",
        description='PRE-REQ: Unlock any Corp tile. Receive any Sigil from the Corporeal Beast',
        icon_path=CONCAT(@icon_path_prefix, 'corp_sigil', @icon_path_postfix)
    WHERE id = 82;
UPDATE tile
    SET title='L3 Warp Tile',description='Good Luck :]',weight=0,icon_path=''
    WHERE id = 83;
UPDATE tile
    SET title='L3 Warp Tile',description='Good Luck :]',weight=0,icon_path=''
    WHERE id = 84;