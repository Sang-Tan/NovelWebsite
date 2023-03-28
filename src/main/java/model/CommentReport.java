package model;

import core.DatabaseObject;

import java.sql.Timestamp;

public class CommentReport implements DatabaseObject {
    private int id;
    private int commentId;
    private int reporterId;
    private String reason;
    private Timestamp checkTime;

    public CommentReport() {
    }

    public CommentReport(int id, int commentId, int reporterId, String reason, Timestamp checkTime) {
        this.id = id;
        this.commentId = commentId;
        this.reporterId = reporterId;
        this.reason = reason;
        this.checkTime = checkTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }
}
