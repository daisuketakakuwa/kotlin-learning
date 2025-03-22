-- organizerテーブル
CREATE TABLE organizer (
    id SERIAL PRIMARY KEY,
    organization_name VARCHAR(300)
);

-- eventテーブル
CREATE TABLE event (
    id BIGSERIAL PRIMARY KEY,
    organizer_id INTEGER REFERENCES organizer(id),
    event_name VARCHAR(300),
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    event_details JSONB
);

-- participantテーブル
CREATE TABLE participant (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    gender CHAR(1)
);

-- rel_event_participantテーブル
CREATE TABLE rel_event_participant (
    event_id BIGINT REFERENCES event(id),
    participant_id BIGINT REFERENCES participant(id),
    PRIMARY KEY (event_id, participant_id)
);
