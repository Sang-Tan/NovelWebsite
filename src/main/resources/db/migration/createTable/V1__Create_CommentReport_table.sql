CREATE TABLE comment_report (
    id INT PRIMARY KEY AUTO_INCREMENT,
    comment_id INT NOT NULL,
    reporter_id INT NOT NULL,
    reason TEXT NOT NULL,
    check_time TIMESTAMP
);
