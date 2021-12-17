DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    user_id      BIGINT                NOT NULL,
    task_list_id BIGINT                NOT NULL,
    title        VARCHAR(255)          NOT NULL,
    done         BOOLEAN   DEFAULT FALSE,
    deadline     TIMESTAMP             NOT NULL,
    created_at   TIMESTAMP DEFAULT NOW(),
    updated_at   TIMESTAMP,
    CONSTRAINT fk_task_lists_tasks FOREIGN KEY (task_list_id) REFERENCES task_lists (id),
    CONSTRAINT fk_users_tasks FOREIGN KEY (user_id) REFERENCES users (id)
);