CREATE TABLE notifications
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT          NOT NULL,
    content VARCHAR(200) NOT NULL,
    link    VARCHAR(200) DEFAULT NULL
);
