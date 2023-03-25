CREATE TABLE tokens
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT       NOT NULL,
    token_hash   CHAR(64)  NOT NULL,
    expired_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (token_hash)

);

