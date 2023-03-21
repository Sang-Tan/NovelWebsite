CREATE TABLE novels
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    owner      INT          NOT NULL,
    summary    TEXT         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    image      VARCHAR(255),
    is_pending BOOLEAN      NOT NULL DEFAULT TRUE,
    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('on going', 'paused', 'finished')),
    UNIQUE (name)
);
