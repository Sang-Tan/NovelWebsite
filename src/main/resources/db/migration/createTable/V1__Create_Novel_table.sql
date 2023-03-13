CREATE TABLE novels
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    owner      INT          NOT NULL,
    summary    TEXT         NOT NULL,
    name       VARCHAR(200) NOT NULL,
    image      VARCHAR(200) NOT NULL,
    is_pending BOOLEAN      NOT NULL DEFAULT TRUE,
    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('in process', 'pause', 'accomplished'))
);
