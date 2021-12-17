DROP TABLE IF EXISTS task_notes;

CREATE TABLE task_notes
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    task_id    BIGINT                NOT NULL,
    note       VARCHAR(255)          NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT fk_task_notes_tasks FOREIGN KEY (task_id) REFERENCES tasks (id)
);