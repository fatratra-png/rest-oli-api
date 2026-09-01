-- Olivia Rodrigo
INSERT INTO artists (id, name) VALUES ('olivia-rodrigo', 'Olivia Rodrigo');

-- SOUR (2021)
INSERT INTO albums (id, title, artist_id, release_date) VALUES ('sour', 'SOUR', 'olivia-rodrigo', '2021-05-21');

INSERT INTO songs (id, title, album_id, release_date, duration_seconds) VALUES
('sour-01', 'brutal', 'sour', '2021-05-21', 143),
('sour-02', 'traitor', 'sour', '2021-05-21', 229),
('sour-03', 'drivers license', 'sour', '2021-05-21', 242),
('sour-04', '1 step forward, 3 steps back', 'sour', '2021-05-21', 163),
('sour-05', 'deja vu', 'sour', '2021-05-21', 215),
('sour-06', 'good 4 u', 'sour', '2021-05-21', 178),
('sour-07', 'enough for you', 'sour', '2021-05-21', 202),
('sour-08', 'happier', 'sour', '2021-05-21', 175),
('sour-09', 'jealousy, jealousy', 'sour', '2021-05-21', 173),
('sour-10', 'favorite crime', 'sour', '2021-05-21', 152),
('sour-11', 'hope ur ok', 'sour', '2021-05-21', 194);

-- GUTS (2023)
INSERT INTO albums (id, title, artist_id, release_date) VALUES ('guts', 'GUTS', 'olivia-rodrigo', '2023-09-08');

INSERT INTO songs (id, title, album_id, release_date, duration_seconds) VALUES
('guts-01', 'all-american bitch', 'guts', '2023-09-08', 170),
('guts-02', 'bad idea right?', 'guts', '2023-09-08', 185),
('guts-03', 'vampire', 'guts', '2023-09-08', 219),
('guts-04', 'lacy', 'guts', '2023-09-08', 176),
('guts-05', 'ballad of a homeschooled girl', 'guts', '2023-09-08', 218),
('guts-06', 'making the bed', 'guts', '2023-09-08', 187),
('guts-07', 'logical', 'guts', '2023-09-08', 156),
('guts-08', 'get him back!', 'guts', '2023-09-08', 188),
('guts-09', 'love is embarrassing', 'guts', '2023-09-08', 191),
('guts-10', 'the grudge', 'guts', '2023-09-08', 271),
('guts-11', 'pretty isn''t pretty', 'guts', '2023-09-08', 174),
('guts-12', 'teenage dream', 'guts', '2023-09-08', 201);

-- GUTS (spilled) (2024) - deluxe with bonus tracks
INSERT INTO albums (id, title, artist_id, release_date) VALUES ('guts-spilled', 'GUTS (spilled)', 'olivia-rodrigo', '2024-03-22');

INSERT INTO songs (id, title, album_id, release_date, duration_seconds) VALUES
('gutss-01', 'obsessed', 'guts-spilled', '2024-03-22', 172),
('gutss-02', 'girl i''ve always been', 'guts-spilled', '2024-03-22', 162),
('gutss-03', 'scared of my guitar', 'guts-spilled', '2024-03-22', 193),
('gutss-04', 'stranger', 'guts-spilled', '2024-03-22', 190),
('gutss-05', 'make it till morning', 'guts-spilled', '2024-03-22', 177);
