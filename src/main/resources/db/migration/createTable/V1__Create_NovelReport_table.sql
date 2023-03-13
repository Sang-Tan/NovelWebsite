CREATE TABLE novel_reports
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    novel_id    INT          NOT NULL,
    reporter_id INT          NOT NULL,
    reason      VARCHAR(200) NOT NULL,
    check_time  TIMESTAMP    NOT NULL
);
