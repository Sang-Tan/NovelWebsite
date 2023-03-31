package model.intermediate;

import model.Chapter;
import model.User;

public class ChapterMark {
    private Chapter chapterId;
    private User userId;

    public ChapterMark() {
    }

    public ChapterMark(Chapter chapterId, User userId) {
        this.chapterId = chapterId;
        this.userId = userId;
    }

    public Chapter getChapterId() {
        return chapterId;
    }

    public void setChapterId(Chapter chapterId) {
        this.chapterId = chapterId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
