ALTER TABLE chapters
    ADD CONSTRAINT FK_chapters_Volume FOREIGN KEY (volume_id)
        REFERENCES volumes (ID) ON DELETE RESTRICT ON UPDATE CASCADE;

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

ALTER TABLE comments
    ADD CONSTRAINT FK_Comment_Chapter FOREIGN KEY (chapter_id)
        REFERENCES chapters (ID) ON DELETE CASCADE ON UPDATE CASCADE;

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
        REFERENCES novels (ID) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE novels
    ADD CONSTRAINT FK_Novel_User FOREIGN KEY (owner)
        REFERENCES users (ID) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE novel_favourite
    ADD CONSTRAINT FK_Bookmark_User FOREIGN KEY (user_id)
        REFERENCES users (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_favourite
    ADD CONSTRAINT FK_Bookmark_Novel FOREIGN KEY (novel_id)
        REFERENCES novels (ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_changes
    ADD CONSTRAINT FK_NovelChange_Novel FOREIGN KEY (novel_id)
        REFERENCES novels (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE volume_changes
    ADD CONSTRAINT FK_VolumeChange_Volume FOREIGN KEY (volume_id)
        REFERENCES volumes (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE chapter_changes
    ADD CONSTRAINT FK_ChapterChange_Chapter FOREIGN KEY (chapter_id)
        REFERENCES chapters (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_approval_logs
    ADD CONSTRAINT FK_NovelApprovalLog_Novel FOREIGN KEY (novel_id)
        REFERENCES novels (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE novel_approval_logs
    ADD CONSTRAINT FK_NovelApprovalLog_Moderator FOREIGN KEY (moderator_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE volume_approval_logs
    ADD CONSTRAINT FK_VolumeApprovalLog_Volume FOREIGN KEY (volume_id)
        REFERENCES volumes (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE volume_approval_logs
    ADD CONSTRAINT FK_VolumeApprovalLog_Moderator FOREIGN KEY (moderator_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE chapter_approval_logs
    ADD CONSTRAINT FK_ChapterApprovalLog_Chapter FOREIGN KEY (chapter_id)
        REFERENCES chapters (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE chapter_approval_logs
    ADD CONSTRAINT FK_ChapterApprovalLog_Moderator FOREIGN KEY (moderator_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE view_in_novel
    ADD CONSTRAINT FK_view_in_novel_novels
        FOREIGN KEY (novel_id) REFERENCES novels (id)
            ON DELETE CASCADE ON UPDATE CASCADE;
