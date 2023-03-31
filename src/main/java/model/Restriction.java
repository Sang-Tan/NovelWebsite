package model;

import core.DatabaseObject;
import repository.UserRepository;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.SQLException;
import java.sql.Timestamp;
@Entity
@Table(name = "restrictions", uniqueConstraints = {@UniqueConstraint(columnNames = {""})})
public class Restriction implements DatabaseObject {
    public static final String TYPE_NOVEL = "novel";
    public static final String TYPE_COMMENT = "comment";
    private User restrictedUser;
    private Integer restrictedUserId;
    private String restrictedType;
    private User executor;
    private Integer executorId;
    private String reason;
    private Timestamp dueTime;

    public Restriction() {
    }

    public Restriction(User restrictedUser, String restrictedType, User executor, String reason, Timestamp dueTime) {
        this.restrictedUser = restrictedUser;
        this.restrictedType = restrictedType;
        this.executor = executor;
        this.reason = reason;
        this.dueTime = dueTime;
    }

    public User getRestrictedUser() throws SQLException {
        restrictedUser = UserRepository.getInstance().getById(restrictedUserId);
        return restrictedUser;
    }

    public void setRestrictedUser(User restrictedUser) {
        this.restrictedUserId = restrictedUser.getId();
        this.restrictedUser = restrictedUser;
    }

    public String getRestrictedType() {
        return restrictedType;
    }

    public void setRestrictedType(String restrictedType) {
        this.restrictedType = restrictedType;
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
        return restrictedUserId;
    }

    public void setRestrictedUserId(Integer restrictedUserId) {
        this.restrictedUserId = restrictedUserId;
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
