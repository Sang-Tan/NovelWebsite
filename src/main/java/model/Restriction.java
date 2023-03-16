package model;

import java.sql.Timestamp;

public class Restriction {
    private User restricterUserId;
    private String restrictedType;
    private User executorId;
    private String reason;
    private Timestamp dueTime;

    public Restriction() {
    }

    public Restriction(User restricterUserId, String restrictedType, User executorId, String reason, Timestamp dueTime) {
        this.restricterUserId = restricterUserId;
        this.restrictedType = restrictedType;
        this.executorId = executorId;
        this.reason = reason;
        this.dueTime = dueTime;
    }

    public User getRestricterUserId() {
        return restricterUserId;
    }

    public void setRestricterUserId(User restricterUserId) {
        this.restricterUserId = restricterUserId;
    }

    public String getRestrictedType() {
        return restrictedType;
    }

    public void setRestrictedType(String restrictedType) {
        this.restrictedType = restrictedType;
    }

    public User getExecutorId() {
        return executorId;
    }

    public void setExecutorId(User executorId) {
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
