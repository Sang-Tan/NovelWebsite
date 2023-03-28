package model;

import core.DatabaseObject;

import java.sql.Timestamp;

public class Comment implements DatabaseObject {
    private int id;
    private int userId;
    private String content;
    private int deactiveBy;
    private Timestamp commentTime;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
