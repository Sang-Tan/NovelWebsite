package model;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private User userId;
    private String content;
    private User deactiveBy;
    private Timestamp commentTime;
    private Comment parentId;

    public Comment() {
    }

    public Comment(int id, User userId, String content, User deactiveBy, Timestamp commentTime, Comment parentId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.deactiveBy = deactiveBy;
        this.commentTime = commentTime;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getDeactiveBy() {
        return deactiveBy;
    }

    public void setDeactiveBy(User deactiveBy) {
        this.deactiveBy = deactiveBy;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public Comment getParentId() {
        return parentId;
    }

    public void setParentId(Comment parentId) {
        this.parentId = parentId;
    }
}
