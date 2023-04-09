package model;

import core.DatabaseObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments", schema = "novelweb")
public class Comment implements DatabaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "chapter_id", nullable = false)
    private int chapterId;


    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "deactive_by")
    private int deactiveBy;
    @Column(name = "time_comment", nullable = false)
    private Timestamp commentTime;
    @Column(name = "parent_id")
    private int parentId;

    public Comment() {
    }

    public Comment(int id, int userId, String content, int deactiveBy, Timestamp commentTime, int parentId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.deactiveBy = deactiveBy;
        this.commentTime = commentTime;
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(User user) {

        this.userId = user.getId();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDeactiveBy() {
        return deactiveBy;
    }

    public void setDeactiveBy(int deactiveBy) {
        this.deactiveBy = deactiveBy;
    }
//    public User getDeactiveByUser() throws SQLException {
//
//        if(deactiveByUser == null) {
//            UserRepository.getInstance().getById(deactiveBy);
//        }
//        return deactiveByUser;
//    }
//
//    public void setDeactiveByUser(User user) {
//
//        this.deactiveByUser = user;
//        deactiveBy = user.getId();
//    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    //    public int getParentComment() throws SQLException {
//
//        if(parentComment == null) {
//            parentComment = CommentRepository.getInstance().getById(parentId);
//        }
//        return parentId;
//    }
//
//    public void setParent(Comment parentComment) {
//        this.parentId = parentId;
//        this.parentComment = parentComment;
//    }
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
