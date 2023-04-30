CREATE TABLE novels
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    owner           INT          NOT NULL,
    summary         TEXT         NOT NULL,
    name            VARCHAR(255) NOT NULL,
    image           VARCHAR(255),
    approval_status VARCHAR(20)  NOT NULL DEFAULT 'pending',
    status          VARCHAR(20)  NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CHECK (approval_status IN ('pending', 'approved', 'rejected')),
    CHECK (status IN ('on going', 'paused', 'finished'))

);
