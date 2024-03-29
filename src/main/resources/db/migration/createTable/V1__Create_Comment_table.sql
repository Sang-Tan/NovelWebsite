CREATE TABLE comments
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT       NOT NULL,
    chapter_id   INT       NOT NULL,
    content      TEXT      NOT NULL,
    deactive_by  INT DEFAULT NULL,
    time_comment TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    parent_id    INT DEFAULT NULL
);
