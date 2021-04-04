CREATE TYPE user_role_type AS ENUM ('ADMIN','USER');


CREATE TABLE player
(
    id         UUID PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT,
    email      TEXT,
    pseudonym  TEXT,
    role       user_role_type NOT NULL
);

CREATE TABLE game
(
    id           UUID PRIMARY KEY,
    player_id    UUID REFERENCES player NOT NULL,
    money        NUMERIC                NOT NULL DEFAULT 0,
    manual_click NUMERIC                NOT NULL DEFAULT 0,
    created_at   TIMESTAMP              NOT NULL,
    updated_at   TIMESTAMP              NOT NULL
);

CREATE TABLE generator
(
    id          UUID PRIMARY KEY,
    name        TEXT,
    description TEXT,
    enabled     BOOLEAN DEFAULT FALSE
);

CREATE TABLE game_generator
(
    id_game      UUID REFERENCES game,
    id_generator UUID REFERENCES generator,
    actual_level INTEGER   NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP NOT NULL,
    PRIMARY KEY (id_game, id_generator)
);

CREATE TABLE generator_cost_per_level
(
    id_generator     UUID REFERENCES generator,
    level            INTEGER NOT NULL,
    cost             NUMERIC NOT NULL,
    click_per_second NUMERIC NOT NULL,
    PRIMARY KEY (id_generator, level)
);