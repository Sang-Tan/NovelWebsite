package model;

import java.sql.Timestamp;

public class CommentReport {
    private int id;
    private Comment commentId;
    private User reporterId;
    private String reason;
    private Timestamp checkTime;

    public CommentReport() {
    }

    public CommentReport(int id, Comment commentId, User reporterId, String reason, Timestamp checkTime) {
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

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }

    public User getReporterId() {
        return reporterId;
    }

    public void setReporterId(User reporterId) {
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
