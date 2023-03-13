CREATE TABLE novel_report
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    novel_id    INT      NOT NULL,
    reporter_id INT      NOT NULL,
    reason      TEXT     NOT NULL,
    check_time  DATETIME NOT NULL
);
