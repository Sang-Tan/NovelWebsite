CREATE TABLE notifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    body TEXT NOT NULL,
    link VARCHAR(200)
);
