CREATE TABLE novel_approval_logs
(
    id         INT          NOT NULL AUTO_INCREMENT,
    novel_id   INT          NOT NULL,
    content    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)