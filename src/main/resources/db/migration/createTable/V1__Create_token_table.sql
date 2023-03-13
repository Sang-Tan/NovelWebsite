CREATE TABLE tokens
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    user_id        INT      NOT NULL,
    validator_hash CHAR(32) NOT NULL
);

