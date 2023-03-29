create database pbl3;
use pbl3;
-- drop database pbl3;

CREATE TABLE chapter_mark (
    chapter_id INT NOT NULL,
    user_id    INT NOT NULL,
    PRIMARY KEY (chapter_id, user_id)
);

CREATE TABLE chapters (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    order_index INT       NOT NULL,
    volume_id   INT       NOT NULL,
    content     TEXT               DEFAULT NULL,
    modify_time TIMESTAMP NOT NULL,
    is_pending  BOOLEAN   NOT NULL DEFAULT TRUE,
    UNIQUE (order_index, volume_id)
);

CREATE TABLE comment_report (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    comment_id  INT          NOT NULL,
    reporter_id INT          NOT NULL,
    reason      VARCHAR(255) NOT NULL,
    check_time  TIMESTAMP DEFAULT NULL,
    UNIQUE (comment_id, reporter_id)
);

CREATE TABLE comments (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT          NOT NULL,
    content      TEXT NOT NULL,
    deactive_by  INT DEFAULT NULL,
    time_comment TIMESTAMP    NOT NULL,
    parent_id    INT DEFAULT NULL
);

CREATE TABLE genres (
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE notifications (
    id      INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT          NOT NULL,
    content VARCHAR(255) NOT NULL,
    link    VARCHAR(255) DEFAULT NULL
);

CREATE TABLE novel_genre (
    genre_id INT NOT NULL,
    novel_id INT NOT NULL,
    PRIMARY KEY (genre_id, novel_id)
);

CREATE TABLE novel_report (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    novel_id    INT          NOT NULL,
    reporter_id INT          NOT NULL,
    reason      VARCHAR(255) NOT NULL,
    check_time  TIMESTAMP    NOT NULL,
    UNIQUE (novel_id, reporter_id)
);

CREATE TABLE novels (
    id         INT PRIMARY KEY AUTO_INCREMENT,
    owner      INT          NOT NULL,
    summary    TEXT         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    image      VARCHAR(255),
    is_pending BOOLEAN      NOT NULL DEFAULT TRUE,
    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('on going', 'paused', 'finished'))
);

CREATE TABLE restrictions (
    restricted_user_id INT          NOT NULL,
    restricted_type    VARCHAR(50)  NOT NULL,
    executor_id        INT          NOT NULL,
    reason             VARCHAR(200) NOT NULL,
    due_time           TIMESTAMP     NOT NULL,
    PRIMARY KEY (restricted_user_id, restricted_type),
    CHECK (restricted_type IN ('comment', 'novel'))
);

CREATE TABLE users (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    password     CHAR(64)    NOT NULL,
    username     VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    is_active    BOOLEAN     NOT NULL DEFAULT TRUE,
    avatar       VARCHAR(200)         DEFAULT NULL,
    role         VARCHAR(20) NOT NULL
        CHECK (role IN ('admin', 'moderator', 'member')),
    UNIQUE (username)
);

CREATE TABLE volumes (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    novel_id    INT          NOT NULL,
    name        VARCHAR(255) NOT NULL,
    image       VARCHAR(255),
    order_index INT          NOT NULL,
        UNIQUE (novel_id, order_index)
);

CREATE TABLE tokens (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    user_id        INT      NOT NULL,
    token_hash CHAR(64) NOT NULL,
    expired_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (token_hash)
);

ALTER TABLE chapters
    ADD CONSTRAINT FK_chapters_Volume FOREIGN KEY (volume_id)
        REFERENCES volumes (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE chapter_mark
    ADD CONSTRAINT FK_chaptersMark_chapters FOREIGN KEY (chapter_id)
        REFERENCES chapters (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE chapter_mark
    ADD CONSTRAINT FK_chaptersMark_User FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT FK_comment_comment1 FOREIGN KEY (parent_id)
        REFERENCES comments (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT FK_comment_User FOREIGN KEY (user_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE comment_report
    ADD CONSTRAINT FK_CommentReport_comment FOREIGN KEY (comment_id)
        REFERENCES comments (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE comment_report
    ADD CONSTRAINT FK_CommentReport_User FOREIGN KEY (reporter_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE notifications
    ADD CONSTRAINT FK_Notification_User FOREIGN KEY (user_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_genre
    ADD CONSTRAINT FK_NovelGenre_Genre FOREIGN KEY (genre_id)
        REFERENCES genres (ID) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE novel_genre
    ADD CONSTRAINT FK_NovelGenre_Novel FOREIGN KEY (novel_id)
        REFERENCES novels (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_report
    ADD CONSTRAINT FK_NovelReport_Novel FOREIGN KEY (novel_id)
        REFERENCES novels (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_report
    ADD CONSTRAINT FK_NovelReport_User FOREIGN KEY (reporter_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE restrictions
    ADD CONSTRAINT FK_Restriction_User FOREIGN KEY (executor_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE restrictions
    ADD CONSTRAINT FK_Restriction_User1 FOREIGN KEY (restricted_user_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE tokens
    ADD CONSTRAINT FK_UserToken_User FOREIGN KEY (user_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE volumes
    ADD CONSTRAINT FK_Volume_Novel FOREIGN KEY (novel_id)
        REFERENCES novels (ID) ON DELETE CASCADE ON UPDATE CASCADE;
        
ALTER TABLE novels
	ADD CONSTRAINT FK_Novel_User foreign key (owner)
		REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;