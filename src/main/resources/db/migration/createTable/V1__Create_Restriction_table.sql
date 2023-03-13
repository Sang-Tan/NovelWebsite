CREATE TABLE restrictions
(
    restricted_user_id INT          NOT NULL,
    restricted_type    VARCHAR(50)  NOT NULL,
    executor_id        INT          NOT NULL,
    reason             VARCHAR(200) NOT NULL,
    due_time           DATETIME     NOT NULL,
    PRIMARY KEY (restricted_user_id, restricted_type)
);
