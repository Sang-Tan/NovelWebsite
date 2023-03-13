CREATE TABLE volumes
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    novel_id     INT          NOT NULL,
    name         VARCHAR(200) NOT NULL,
    image        VARCHAR(200) NOT NULL,
    volume_order SMALLINT     NOT NULL
);
