package model;

import java.sql.Timestamp;

public class NovelReport {
    private int id;
    private Novel novelId;
    private User reporterId;
    private String reason;
    private Timestamp checkTime;

    public NovelReport() {
    }

    public NovelReport(int id, Novel novelId, User reporterId, String reason, Timestamp checkTime) {
        this.id = id;
        this.novelId = novelId;
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

    public Novel getNovelId() {
        return novelId;
    }

    public void setNovelId(Novel novelId) {
        this.novelId = novelId;
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
