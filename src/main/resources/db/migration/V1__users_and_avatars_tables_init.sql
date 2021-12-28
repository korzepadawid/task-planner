DROP TABLE IF EXISTS avatars;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    auth_provider VARCHAR(255)          NOT NULL,
    password      VARCHAR(100)          NOT NULL,
    email         VARCHAR(320) UNIQUE   NOT NULL,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP
);

CREATE TABLE avatars
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    storage_key VARCHAR(255),
    user_id     BIGINT UNIQUE         NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW(),
    updated_at  TIMESTAMP,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users (id)
);