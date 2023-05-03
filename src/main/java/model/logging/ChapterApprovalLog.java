package model.logging;

import core.DatabaseObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "chapter_approval_logs")
public class ChapterApprovalLog implements IApprovalLog, DatabaseObject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "chapter_id")
    private Integer chapterId;
    @Basic
    @Column(name = "moderator_id")
    private Integer moderatorId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdAt) {
        this.createdTime = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChapterApprovalLog that = (ChapterApprovalLog) o;
        return Objects.equals(id, that.id) && Objects.equals(chapterId, that.chapterId) && Objects.equals(moderatorId, that.moderatorId) && Objects.equals(content, that.content) && Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chapterId, moderatorId, content, createdTime);
    }
}
