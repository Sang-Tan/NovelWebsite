CREATE TABLE volumes
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    novel_id        INT          NOT NULL,
    name            VARCHAR(255) NOT NULL,
    image           VARCHAR(255),
    order_index     INT          NOT NULL,
    approval_status VARCHAR(20)  NOT NULL DEFAULT 'pending',
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CHECK (approval_status IN ('pending', 'approved', 'rejected')),
    UNIQUE (novel_id, order_index)
);
