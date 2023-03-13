CREATE TABLE comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    deactive_by INT,
    time_comment TIMESTAMP NOT NULL,
    parent_id INT
);
