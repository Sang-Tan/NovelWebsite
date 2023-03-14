CREATE TABLE chapter_mark
(
    chapter_id INT NOT NULL,
    user_id    INT NOT NULL,
    PRIMARY KEY (chapter_id, user_id)
);