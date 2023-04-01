package model.intermediate;

import model.Chapter;
import model.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ChapterMark {
    private Chapter chapter;
    private User user;

    public ChapterMark() {
    }

    public ChapterMark(Chapter chapter, User user) {
        this.chapter = chapter;
        this.user = user;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
