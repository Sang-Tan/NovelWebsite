package model;

import core.DatabaseObject;
import repository.CommentRepository;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;
@Entity
@Table(name = "comment_report", schema = "novelweb")
public class CommentReport implements DatabaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "comment_id", nullable = false)
    private int commentId;
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    private Comment comment;

    @Column(name = "reporter_id", nullable = false)
    private int reporterId;
    @ManyToOne
    @JoinColumn(name = "reporter_id", referencedColumnName = "id", nullable = false)
    private User reporter;
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "check_time")
    private Timestamp checkTime;
    @Column(name = "report_time", nullable = false)
    private Timestamp reportTime;
    public CommentReport() {
    }

    public CommentReport(int id, int commentId, int reporterId, String reason, Timestamp checkTime, Timestamp reportTime) {
        this.id = id;
        this.commentId = commentId;
        this.reporterId = reporterId;
        this.reason = reason;
        this.checkTime = checkTime;
        this.reportTime = reportTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment getComment() throws SQLException {

        if(comment == null) {
            comment = CommentRepository.getInstance().getById(commentId);
        }
        return comment;
    }

    public void setComment(Comment comment) {

        this.comment = comment;
        commentId = comment.getId();
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getReporter() throws SQLException {

        if(reporter == null) {
            reporter = UserRepository.getInstance().getById(reporterId);
        }
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporterId = reporter.getId();
        this.reporter = reporter;
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

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }
}
