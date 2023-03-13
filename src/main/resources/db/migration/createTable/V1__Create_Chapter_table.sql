    [ChapterOrder] [smallint] NOT NULL,
CREATE TABLE chapters
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    order_index INT       NOT NULL,
    volume_id   INT       NOT NULL,
    content     TEXT               DEFAULT NULL,
    modify_time TIMESTAMP NOT NULL,
    is_pending  BOOLEAN   NOT NULL DEFAULT TRUE,
    UNIQUE (order_index, volume_id)
);
