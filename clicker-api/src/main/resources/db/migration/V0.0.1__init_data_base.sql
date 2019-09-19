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