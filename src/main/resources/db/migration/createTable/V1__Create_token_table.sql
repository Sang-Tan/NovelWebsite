CREATE TABLE tokens
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT      NOT NULL,
    token_hash CHAR(64) NOT NULL,
    expired_time    DATETIME NOT NULL,
    UNIQUE (token_hash)
);

