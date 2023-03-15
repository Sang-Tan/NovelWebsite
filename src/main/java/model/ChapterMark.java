package model;

public class ChapterMark {
    private int chapterId;
    private int userId;

    public ChapterMark() {
    }

    public ChapterMark(int chapterId, int userId) {
        this.chapterId = chapterId;
        this.userId = userId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
