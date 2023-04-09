CREATE TABLE novel_favourite
(
    user_id  INT NOT NULL,
    novel_id INT NOT NULL,
    PRIMARY KEY (user_id, novel_id)
);