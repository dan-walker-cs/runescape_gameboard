-- Winter 2025 tile insertion

SET @icon_path_prefix = 'assets/icons/';
SET @icon_path_postfix = '_tile_icon.png';

INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Start',"Good Luck!",0, '');
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Oh Brother: Guthan',"Collect any piece of Guthan's barrows set",1,
    CONCAT(@icon_path_prefix, 'guthan', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Oh Brother: Dharok',"Collect any piece of Dharok's barrows set",1,
    CONCAT(@icon_path_prefix, 'dharok', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Oh Brother: Verac',"Collect any piece of Verac's barrows set",1,
    CONCAT(@icon_path_prefix, 'verac', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Oh Brother: Torag',"Collect any piece of Torag's barrows set",1,
    CONCAT(@icon_path_prefix, 'torag', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Oh Brother: Karil',"Collect any piece of Karil's barrows set",1,
    CONCAT(@icon_path_prefix, 'karil', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Oh Brother: Ahrim',"Collect any piece of Ahrim's barrows set",1,
    CONCAT(@icon_path_prefix, 'ahrim', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("That's No Moon: Eclipse",'Collect any piece of the Eclipse Moon set',1,
    CONCAT(@icon_path_prefix, 'eclipse_moon', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("That's No Moon: Blue",'Collect any piece of the Blue Moon set',1,
    CONCAT(@icon_path_prefix, 'blue_moon', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("That's No Moon: Blood",'Collect any piece of the Blood Moon set',1,
    CONCAT(@icon_path_prefix, 'blood_moon', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Pick in yo Lock Twin','Find a Strange Old Lockpick in the Hallowed Sepulchre',1,
    CONCAT(@icon_path_prefix, 'strange_lockpick', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('..Ice...Boppers..','Receive the Glacial Temolti from Amoxliatl',1,
    CONCAT(@icon_path_prefix, 'glacial_temolti', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('..Earth..Boppers..','Receive the Earthbound Tecpatl from Earthen Nagua',1,
    CONCAT(@icon_path_prefix, 'earthbound_tecpatl', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('..Fire..Boppers..','Receive the Sulphur Blades from Sulphur Nagua',1,
    CONCAT(@icon_path_prefix, 'sulphur_blades', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('My Deer Janitor','Receive the Antler Guard from Custodian Stalkers',1,
    CONCAT(@icon_path_prefix, 'antler_guard', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Old Uncle Eldricky','Receive either the prayer scroll or staff piece from Eldric',1,
    CONCAT(@icon_path_prefix, 'royal_titan_ice', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('My Braaaand..a','Receive either the prayer scroll or staff piece from Branda',1,
    CONCAT(@icon_path_prefix, 'royal_titan_fire', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Doubling gp!!1@!','Double the GP of 5 players at w301 GE HONESTLY',1,
    CONCAT(@icon_path_prefix, 'gold_pieces', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('The Winter Toad','Receive any non-page unique from subduing the Wintertodt',1,
    CONCAT(@icon_path_prefix, 'wintertodt', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Salamandeez','Catch a Tecu Salamander',1,
    CONCAT(@icon_path_prefix, 'tecu_salamander', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Berserker...ous Digit','Receive a Berserker Ring from Dagannoth Rex',1,
    CONCAT(@icon_path_prefix, 'berserker_ring', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Is That Blood On Your Robes?','Receive a piece of Elder Chaos set',1,
    CONCAT(@icon_path_prefix, 'elder_chaos', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("I Could've Just Done LMS",'Receive a Crystal Weapon Seed from the Gauntlet - either difficulty',1,
    CONCAT(@icon_path_prefix, 'weapon_seed', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Worrious Digit','Receive a Warrior Ring from Dagannoth Rex',1,
    CONCAT(@icon_path_prefix, 'warrior_ring', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('More Monkeying Around','Complete 500 laps of the Ape Atoll Agility Course',1,
    CONCAT(@icon_path_prefix, 'monkey_laps', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Malodorous Mince','Receive any shard of the Odium or Malediction ward from the Wilderness demi-bosses',1,
    CONCAT(@icon_path_prefix, 'odium_malediction_shard', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('We Were On a Break!','Receive any non-page, set, and flake unique from subduing the Tempoross',1,
    CONCAT(@icon_path_prefix, 'tempoross', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Archerous Digit','Receive an Archers Ring from Dagannoth Supreme',2,
    CONCAT(@icon_path_prefix, 'archer_ring', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('More Than a Spiced Rum','Receive any unique from the Kraken boss',2,
    CONCAT(@icon_path_prefix, 'kraken', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('I Will Use Your Limbs As A Crossbow','Receive Dragon Limbs as a drop from Rune or Adamant dragons',2,
    CONCAT(@icon_path_prefix, 'dragon_limbs', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Go For a Dip','Receive an unsired from the Abyssal Sire slayer boss',2,
    CONCAT(@icon_path_prefix, 'unsired', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Enough for a Necklace','Collect 100 Molch Pearls from Aerial Fishing',2,
    CONCAT(@icon_path_prefix, 'aerial_fishing_pearls', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('My Fucking Eyes: Zamorak',"Receive any non-shard unique from K'ril Tsutsaroth in the God Wars Dungeon",2,
    CONCAT(@icon_path_prefix, 'zammy_gwd', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Jaw Dropper','Receive the Basilisk Jaw as a drop from Basilisk Knights',2,
    CONCAT(@icon_path_prefix, 'basilisk_jaw', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('My Fucking Eyes: Bandos','Receive any non-shard unique from General Graardor in the God Wars Dungeon',2,
    CONCAT(@icon_path_prefix, 'bandos_gwd', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Four Quartz Tuah Gallon: Blood','Receive the Blood Quartz as a drop from Vardorvis',2,
    CONCAT(@icon_path_prefix, 'dt2_blood', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Four Quartz Tuah Gallon: Smoke','Receive the Smoke Quartz as a drop from The Leviathan',2,
    CONCAT(@icon_path_prefix, 'dt2_smoke', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Wizardous Digit','Receive a Seers Ring from Dagannoth Prime',2,
    CONCAT(@icon_path_prefix, 'seer_ring', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Rocks In My Shoes','Receive any crystal / stone from Cerberus',2,
    CONCAT(@icon_path_prefix, 'cerberus', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Prison Jumpsuit','Receive a Crystal Armour Seed from the Gauntlet - either difficulty',2,
    CONCAT(@icon_path_prefix, 'armour_seed', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Receive Head..probably','Receive any unique from Vorkath - including the head!',2,
    CONCAT(@icon_path_prefix, 'vorkath', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('The Mask starring Jim Carrey','Receive any unique from the Thermonuclear Smoke Devil',2,
    CONCAT(@icon_path_prefix, 'thermy', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('My Fucking Eyes: Saradomin','Receive any non-shard unique from Commander Zilyana in the God Wars Dungeon',2,
    CONCAT(@icon_path_prefix, 'sara_gwd', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Autobots Roll Out','Receive a Blood Shard in Darkmeyer, by either theft or murder',2,
    CONCAT(@icon_path_prefix, 'blood_shard', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('My Fucking Eyes: Armadyl',"Receive any non-shard unique from Kree'arra in the God Wars Dungeon",2,
    CONCAT(@icon_path_prefix, 'arma_gwd', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Four Quartz Tuah Gallon: Shadow','Receive the Shadow Quartz as a drop from The Whisperer',2,
    CONCAT(@icon_path_prefix, 'dt2_shadow', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Four Quartz Tuah Gallon: Ice','Receive the Ice Quartz as a drop from Duke Sucellus',2,
    CONCAT(@icon_path_prefix, 'dt2_ice', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Californian Art','Receive either the ring, claws, or hilt from Callisto or Artio',2,
    CONCAT(@icon_path_prefix, 'callisto', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Am I a Real Ironman Now?','Receive any unique from Zulrah - other than scales and scrolls',2,
    CONCAT(@icon_path_prefix, 'zulrah', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Venetian Spine','Receive either the ring, fangs, or gem from Venenatis or Spindel',2,
    CONCAT(@icon_path_prefix, 'venenatis', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Most Recent Spider Boss','Receive any unique from Araxxor - other than sacks, scrolls, and head',2,
    CONCAT(@icon_path_prefix, 'araxxor', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('I Will Use Your Body Parts As Weapons','Receive the Burning Claw or Tormented Synapse from Tormented Demons',2,
    CONCAT(@icon_path_prefix, 'tormented_demon', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Veritable Calves',"Receive either the ring, skull, or blade from Vet'ion or Calvar'ion",2,
    CONCAT(@icon_path_prefix, 'vetion', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Slimy Sigil','Receive the Ancient Icon from Phantom Muspah',2,
    CONCAT(@icon_path_prefix, 'muspah_icon', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Do Dragons Skill?','Receive the Dragon Pickaxe from any PvM source',2,
    CONCAT(@icon_path_prefix, 'dragon_pick', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('So No Head?','Receive any unique from the Alchemical Hydra - other than throwables and heads',2,
    CONCAT(@icon_path_prefix, 'hydra', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Become Holy','Receive a Holy Elixir from the Corporeal Beast',2,
    CONCAT(@icon_path_prefix, 'corp_elixir', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("Don't Get It Twisted",'Receive any non-mega-rare purple chest unique from the Chambers of Xeric',3,
    CONCAT(@icon_path_prefix, 'cox_normal', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Groundhog Day','Receive any non-mega-rare purple chest unique from the Tombs of Amascut',3,
    CONCAT(@icon_path_prefix, 'toa_normal', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Fake Money Snake','Receive any unique from The Leviathan - other than orbs, tablet, and quartz',3,
    CONCAT(@icon_path_prefix, 'leviathan', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Ax-ing for a Friend','Receive any unique from Vardorvis - other than orbs, tablet, and quartz',3,
    CONCAT(@icon_path_prefix, 'vardorvis', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Duke Nukem','Receive any unique from Duke Sucellus - other than orbs, tablet, and quartz',3,
    CONCAT(@icon_path_prefix, 'duke', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Do Players Skill?','Receive any skilling pet - skilling bosses count! (no chompy chick)',3,
    CONCAT(@icon_path_prefix, 'pet_skilling', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Beyond the Shadow of a Doubt','Receive any mega-rare purple chest unique from the Tombs of Amascut',3,
    CONCAT(@icon_path_prefix, 'toa_mega', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Get It Twisted','Receive any mega-rare purple chest unique from the Chambers of Xeric',3,
    CONCAT(@icon_path_prefix, 'cox_mega', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('See Examine Text','Find a Ring of Endurance in the Hallowed Sepulchre',3,
    CONCAT(@icon_path_prefix, 'endurance_ring', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("Korasi's Lament",'Collect all 3 pieces of Voidwaker from the Wilderness bosses',3,
    CONCAT(@icon_path_prefix, 'voidwaker_complete', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Maybe Nex Time','Receive any non-shard unique from Nex in the God Wars Dungeon',3,
    CONCAT(@icon_path_prefix, 'nex_gwd', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('I Thought It Was 5','Receive a "full" Venator Bow (3 shards) from Phantom Muspah',3,
    CONCAT(@icon_path_prefix, 'muspah_venator_full', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Become Horny (or Oathy)','Receive the Soulflame Horn or any Oathplate piece from Yama',3,
    CONCAT(@icon_path_prefix, 'yama', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Bring Me to Life (Wake Me Up Inside)',"Receive any unique from The Nightmare or Phosani's Nightmare - other than tablet",3,
    CONCAT(@icon_path_prefix, 'nightmare', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Malodorous Masterpiece','Receive all 3 pieces of either the Odium or Malediction Ward from Wilderness demi-bosses',3,
    CONCAT(@icon_path_prefix, 'odium_malediction_complete', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Do Elves Skill?','Receive a Crystal Tool Seed or Zalcano Shard from Zalcano',3,
    CONCAT(@icon_path_prefix, 'tool_seed', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("Sitter's Euphoria",'Receive any Sigil from the Corporeal Beast',3,
    CONCAT(@icon_path_prefix, 'corp_sigil', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('You Feared the Reaper (because no scythe)','Receive any non-mega-rare purple chest unique from the Theatre of Blood',3,
    CONCAT(@icon_path_prefix, 'tob_normal', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Prisonbreak!','Receive an Enhanced Crystal Weapon Seed from the Gauntlet - either difficulty',3,
    CONCAT(@icon_path_prefix, 'enhanced_weapon_seed', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Something Something Invader Zim','Receive the Avernic Treads, Eye of Ayak, or Mokhaiotl Cloth from Doom of Mokhaiotl',3,
    CONCAT(@icon_path_prefix, 'doom_mokhaiotl', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("I Can't Hear You",'Receive any unique from The Whisperer - other than orbs, tablet, and quartz',3,
    CONCAT(@icon_path_prefix, 'whisperer', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Red Ham and Eggs','Receive the Dragon Warhammer from Lizardman Shamans',3,
    CONCAT(@icon_path_prefix, 'dragon_warhammer', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ("Rada's Regret",'Receive the Golden Tench from Aerial Fishing',3,
    CONCAT(@icon_path_prefix, 'golden_tench', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Future Clue Step','Receive every piece of either the Light or Heavy Ballista from Demonic Gorillas',3,
    CONCAT(@icon_path_prefix, 'light_heavy_ballista', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('A Scythe of Relief','Receive any mega-rare purple chest unique from the Theatre of Blood',3,
    CONCAT(@icon_path_prefix, 'tob_mega', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('I Will Use Your Helmet As A Helmet','Receive the Dragon Fullhelm from offering Chewed Bones',3,
    CONCAT(@icon_path_prefix, 'dragon_fullhelm', @icon_path_postfix));
INSERT INTO tile (`title`, `description`, `weight`, `icon_path`)
    VALUES ('Smaller Version of the Thing You Killed','Receive any boss pet (no chompy chick)',3,
    CONCAT(@icon_path_prefix, 'pet_bossing', @icon_path_postfix));