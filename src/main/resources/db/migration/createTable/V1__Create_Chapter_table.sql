CREATE TABLE chapters
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    order_index     INT          NOT NULL,
    volume_id       INT          NOT NULL,
    content         TEXT                  DEFAULT NULL,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    approval_status VARCHAR(20)  NOT NULL DEFAULT 'pending',
    CHECK (approval_status IN ('pending', 'approved', 'rejected')),
    UNIQUE (order_index, volume_id)
);