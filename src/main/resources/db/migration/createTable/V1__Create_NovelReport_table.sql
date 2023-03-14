CREATE TABLE novel_report
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    novel_id    INT          NOT NULL,
    reporter_id INT          NOT NULL,
    reason      VARCHAR(255) NOT NULL,
    check_time  TIMESTAMP    DEFAULT NULL,
    UNIQUE (novel_id, reporter_id)
);
