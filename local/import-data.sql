DELETE FROM rel_event_participant;
DELETE FROM participant;
DELETE FROM event;
DELETE FROM organizer;

ALTER SEQUENCE organizer_id_seq RESTART WITH 1;
ALTER SEQUENCE event_id_seq RESTART WITH 1;
ALTER SEQUENCE participant_id_seq RESTART WITH 1;

-- organizer
INSERT INTO organizer (organization_name) VALUES
    ('組織1'),('組織2'),('組織3'),('組織4'),('組織5');

-- event
INSERT INTO event (organizer_id, event_name, starts_at, ends_at, event_details) VALUES
    (1, 'イベント1', '2024-05-02 10:30:00', '2024-05-02 12:30:00', 
        '{"description": "This event is amazing", "address1": "123 Main St", "address2": "City, Country"}'),
    (1, 'イベント2', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is awesome", "address1": "456 Elm St", "address2": "City, Country"}'),
    (2, 'イベント3', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is fantastic", "address1": "789 Oak St", "address2": "City, Country"}'),
    (2, 'イベント4', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is great", "address1": "1011 Pine St", "address2": "City, Country"}'),
    (3, 'イベント5', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is wonderful", "address1": "1213 Cedar St", "address2": "City, Country"}'),
    (3, 'イベント6', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is spectacular", "address1": "1415 Maple St", "address2": "City, Country"}'),
    (4, 'イベント7', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is incredible", "address1": "1617 Walnut St", "address2": "City, Country"}'),
    (5, 'イベント8', '2024-05-02 10:30:00', '2024-05-02 12:30:00',
        '{"description": "This event is superb", "address1": "1819 Birch St", "address2": "City, Country"}');

-- participant
INSERT INTO participant(first_name, last_name, gender) VALUES
	('LeBron','James', 'M'),
	('Stephen','Curry', 'M'),
	('Kevin','Durant', 'M'),
	('Giannis','Antetokounmpo', 'M'),
	('Kawhi','Leonard', 'M'),
	('Luka','Don?i?', 'M'),
	('Diana','Taurasi', 'F'),
	('Sue','Bird', 'F'),
	('Breanna','Stewart', 'F'),
	('Aja','Wilson', 'F');

-- rel_event_participant
INSERT INTO rel_event_participant VALUES
    -- Lebron
    (1, 1),
    (2, 1),
    (3, 1),
    -- Curry
    (4, 2),
    (5, 2),
    (6, 2),
    -- Durant
    (2, 3),
    (3, 3),
    (4, 3),
    -- Antetokounmpo
    (6, 4),
    (7, 4),
    (8, 4),
    -- Leonard
    (3, 5),
    (4, 5),
    (5, 5),
    -- Don?i?
    (2, 6),
    (3, 6),
    (4, 6),
    -- Taurasi
    (5, 7),
    (6, 7),
    (7, 7),
    -- Bird
    (4, 8),
    (5, 8),
    (6, 8),
    -- Stewart
    (1, 9),
    (2, 9),
    (3, 9),
    -- Wilson
    (1, 10),
    (5, 10),
    (6, 10);

