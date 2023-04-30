CREATE TABLE novel_changes
(
    novel_id int PRIMARY KEY NOT NULL,
    summary  TEXT         DEFAULT NULL,
    name     VARCHAR(255) DEFAULT NULL,
    image    VARCHAR(255) DEFAULT NULL
);