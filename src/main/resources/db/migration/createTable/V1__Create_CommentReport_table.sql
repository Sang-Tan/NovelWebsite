CREATE TABLE comment_reports
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    comment_id  INT          NOT NULL,
    reporter_id INT          NOT NULL,
    reason      VARCHAR(200) NOT NULL,
    check_time  TIMESTAMP DEFAULT NULL,
    UNIQUE (comment_id, reporter_id)
);
