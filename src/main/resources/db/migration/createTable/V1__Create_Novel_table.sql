CREATE TABLE novels (
    id INT PRIMARY KEY AUTO_INCREMENT,
    owner INT NOT NULL,
    summary TEXT NOT NULL,
    summary_temp TEXT,
    name VARCHAR(200) NOT NULL,
    name_temp VARCHAR(200),
    image VARCHAR(200) NOT NULL,
    image_temp VARCHAR(200),
    is_pending BOOLEAN NOT NULL,
    status VARCHAR(50) NOT NULL
);
