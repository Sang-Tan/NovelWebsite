ALTER TABLE chapters ADD CONSTRAINT FK_chapters_Volume FOREIGN KEY (volume_id)
    REFERENCES volumes (ID);

ALTER TABLE chapter_marks  ADD CONSTRAINT FK_chaptersMark_chapters FOREIGN KEY (chapter_id)
    REFERENCES chapters (id);

ALTER TABLE chapter_marks  ADD CONSTRAINT FK_chaptersMark_User FOREIGN KEY (user_id)
    REFERENCES users (id);

ALTER TABLE comments  ADD CONSTRAINT FK_comment_comment1 FOREIGN KEY (parent_id)
    REFERENCES comments (ID);

ALTER TABLE comments  ADD CONSTRAINT FK_comment_User FOREIGN KEY (user_id)
    REFERENCES users (ID);

ALTER TABLE comment_reports  ADD CONSTRAINT FK_CommentReport_comment FOREIGN KEY (comment_id)
    REFERENCES comments (ID);

ALTER TABLE comment_reports  ADD CONSTRAINT FK_CommentReport_User FOREIGN KEY (reporter_id)
    REFERENCES users (ID);

ALTER TABLE notifications  ADD CONSTRAINT FK_Notification_User FOREIGN KEY (user_id)
    REFERENCES users (ID);

ALTER TABLE novel_genres  ADD CONSTRAINT FK_NovelGenre_Genre FOREIGN KEY (genre_id)
    REFERENCES genres (ID);

ALTER TABLE novel_genres  ADD CONSTRAINT FK_NovelGenre_Novel FOREIGN KEY (novel_id)
    REFERENCES novels (ID);

ALTER TABLE novel_reports  ADD CONSTRAINT FK_NovelReport_Novel FOREIGN KEY (novel_id)
    REFERENCES novels (ID);

ALTER TABLE novel_reports  ADD CONSTRAINT FK_NovelReport_User FOREIGN KEY (reporter_id)
    REFERENCES users (ID);

ALTER TABLE restrictions  ADD CONSTRAINT FK_Restriction_User FOREIGN KEY (executor_id)
    REFERENCES users (ID);

ALTER TABLE restrictions  ADD CONSTRAINT FK_Restriction_User1 FOREIGN KEY (restricted_user_id)
    REFERENCES users (ID);

ALTER TABLE tokens  ADD CONSTRAINT FK_UserToken_User FOREIGN KEY (user_id)
    REFERENCES users (ID);

ALTER TABLE volumes  ADD CONSTRAINT FK_Volume_Novel FOREIGN KEY (novel_id)
    REFERENCES novels (ID);

ALTER TABLE comments  ADD CONSTRAINT FK_comment_comment FOREIGN KEY (parent_id)
    REFERENCES comments (ID);