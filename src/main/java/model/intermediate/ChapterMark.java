package model.intermediate;

import core.DatabaseObject;
import model.Chapter;
import model.User;
import repository.ChapterRepository;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;

@Entity
@Table(name = "chapter_mark", schema = "novelweb")
public class ChapterMark implements DatabaseObject {
    @Id
    @Column(name = "chapter_id")
    private int chapterId;
    @Id
    @Column(name = "user_id")
    private int userId;
    @ManyToOne()
    @JoinColumn(name = "chapter_id", referencedColumnName = "id", nullable = false)
    private Chapter relatedChapter;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User relatedUser;

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Chapter getRelatedChapter() throws SQLException {
        if (relatedChapter == null || relatedChapter.getId() != chapterId)
            relatedChapter = ChapterRepository.getInstance().getById(chapterId);
        return relatedChapter;
    }

    public void setRelatedChapter(Chapter chapter) {
        this.chapterId = chapterId;
        this.relatedChapter = chapter;
    }

    public int getUserId() {
        return userId;
    }

    public User getUser() throws SQLException {
        if (relatedUser == null || relatedUser.getId() != userId)
            relatedUser = UserRepository.getInstance().getById(userId);
        return relatedUser;
    }


    public void setUser(User user) {

        this.userId = userId;
        this.relatedUser = user;
    }

}
