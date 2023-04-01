package model.intermediate;

import core.DatabaseObject;
import model.User;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Table(name = "restrictions", uniqueConstraints = {@UniqueConstraint(columnNames = {"restricted_user_id", "restricted_type"})})
public class Restriction implements DatabaseObject {
    public static final String TYPE_NOVEL = "novel";
    public static final String TYPE_COMMENT = "comment";
    @ManyToOne
    @JoinColumn(name = "restricted_user_id")
    private User restrictedUser;
    @EmbeddedId
    private RestrictionKey restrictionKey;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;
    @Column(name = "executor_id")
    private Integer executorId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "due_time")
    private Timestamp dueTime;

    public Restriction() {
    }

    public Restriction(User restrictedUser, String restrictedType, User executor, String reason, Timestamp dueTime) {
        this.restrictionKey.restrictedUserId = restrictedUser.getId();
        this.restrictionKey.restrictedType = restrictedType;
        this.executor = executor;
        this.reason = reason;
        this.dueTime = dueTime;
    }

    public User getRestrictedUser() throws SQLException {
        restrictedUser = UserRepository.getInstance().getById(restrictionKey.restrictedUserId);
        return restrictedUser;
    }

    public void setRestrictedUser(User restrictedUser) {
        this.restrictionKey.restrictedUserId = restrictedUser.getId();
        this.restrictedUser = restrictedUser;
    }

    public String getRestrictedType() {
        return restrictionKey.restrictedType;
    }

    public void setRestrictedType(String restrictedType) {
        this.restrictionKey.restrictedType = restrictedType;
    }

    public User getExecutor() throws SQLException {
        executor = UserRepository.getInstance().getById(executorId);
        return executor;
    }

    public void setExecutor(User executor) {
        this.executorId = executor.getId();
        this.executor = executor;
    }

    public Integer getRestrictedUserId() {
        return restrictionKey.restrictedUserId;
    }

    public void setRestrictedUserId(Integer restrictedUserId) {
        this.restrictionKey.restrictedUserId = restrictedUserId;
    }

    public Integer getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }
}
