package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "novel_report", schema = "novelweb")
public class NovelReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    //TODO : ...
    private int novelId;
    private int reporterId;
    private String reason;
    private Timestamp checkTime;

    public NovelReport() {
    }

    public NovelReport(int id, int novelId, int reporterId, String reason, Timestamp checkTime) {
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

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
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
