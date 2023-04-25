CREATE TABLE volume_changes
(
    volume_id INT PRIMARY KEY,
    name      VARCHAR(255) DEFAULT NULL,
    image     VARCHAR(255) DEFAULT NULL
);