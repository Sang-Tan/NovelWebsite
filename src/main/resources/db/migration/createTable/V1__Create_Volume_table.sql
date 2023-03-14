CREATE TABLE volumes
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    novel_id    INT          NOT NULL,
    name        VARCHAR(255) NOT NULL,
    image       VARCHAR(255),
    order_index INT          NOT NULL
        UNIQUE (novel_id, volume_order)
);
