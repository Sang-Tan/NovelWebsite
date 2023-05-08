package model;

import core.DatabaseObject;
import repository.NovelRepository;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Table(name = "novel_report")
public class NovelReport implements DatabaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    //TODO : ...
    @ManyToOne
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novel novel;
    @Column(name = "novel_id", nullable = false)
    private int novelId;
    @ManyToOne
    @JoinColumn(name = "reporter_id", referencedColumnName = "id", nullable = false)
    private User reporter;
    @Column(name = "reporter_id", nullable = false)
    private int reporterId;
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "check_time")
    private Timestamp checkTime;
    @Column(name = "report_time", nullable = false)
    private Timestamp reportTime;

    public NovelReport() {
    }

    public NovelReport(int id, int novelId, int reporterId, String reason, Timestamp checkTime, Timestamp reportTime) {
        this.id = id;
        this.novelId = novelId;
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

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public Novel getNovel() throws SQLException {

        if (novel == null) {
            novel = NovelRepository.getInstance().getById(reporterId);
        }
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novelId = novel.getId();
        this.novel = novel;
    }

    public User getReporter() throws SQLException {

        if (reporter == null) {
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
