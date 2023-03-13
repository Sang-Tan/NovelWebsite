CREATE TABLE restrictions
(
    restricted_user_id INT         NOT NULL,
    restricted_type    VARCHAR(50) NOT NULL,
    executor_id        INT         NOT NULL,
    reason             TEXT        NOT NULL,
    due_time           TIMESTAMP   NOT NULL
);
