CREATE TABLE users
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    password     CHAR(32)    NOT NULL,
    user_name    VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    is_active    BIT         NOT NULL,
    avatar       VARCHAR(200) NULL,
    role         NVARCHAR(100) NOT NULL
);
