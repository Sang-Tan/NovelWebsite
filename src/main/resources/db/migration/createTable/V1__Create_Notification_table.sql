CREATE TABLE notifications
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT          NOT NULL,
    content VARCHAR(255) NOT NULL,
    link    VARCHAR(255) DEFAULT NULL,
    time_push_notif TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
