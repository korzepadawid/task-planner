DROP TABLE IF EXISTS task_lists;

CREATE TABLE task_lists
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    title      VARCHAR(255)          NOT NULL,
    user_id    BIGINT                NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT fk_user_task_lists FOREIGN KEY (user_id) REFERENCES users (id)
);