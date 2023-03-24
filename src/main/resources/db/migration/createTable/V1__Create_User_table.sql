CREATE TABLE users
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    password     CHAR(64)    NOT NULL,
    username     VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    is_active    BOOLEAN     NOT NULL DEFAULT TRUE,
    avatar       VARCHAR(200)         DEFAULT NULL,
    role         VARCHAR(20) NOT NULL
        CHECK (role IN ('admin', 'moderator', 'member')),
    UNIQUE (username)

);
