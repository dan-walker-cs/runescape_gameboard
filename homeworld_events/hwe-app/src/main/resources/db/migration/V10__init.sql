-- Update event buy-in
UPDATE event
    SET buy_in = "22m/player"
    WHERE id = 1;

-- Update team names
UPDATE team
    SET name = 'Organized Crime'
    WHERE event_id = 1 AND name = 'TEAM_ONE';
UPDATE team
    SET name = "Verzik's OnlyFans"
    WHERE event_id = 1 AND name = 'TEAM_TWO';