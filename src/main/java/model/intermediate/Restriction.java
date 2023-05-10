package model.intermediate;

import core.DatabaseObject;
import core.metadata.JSONSerializable;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "restrictions", uniqueConstraints = {@UniqueConstraint(columnNames = {"restricted_user_id", "restricted_type"})})
public class Restriction implements DatabaseObject, JSONSerializable {
    public static final String TYPE_NOVEL = "novel";
    public static final String TYPE_COMMENT = "comment";

    @Id
    @Column(name = "restricted_user_id")
    private int restrictedUserId;

    @Id
    @Column(name = "restricted_type")
    private String restrictedType;

    @Column(name = "executor_id")
    private int executorId;


    @Column(name = "reason")
    private String reason;
    @Column(name = "due_time")
    private Timestamp dueTime;

    public Restriction() {
    }

    public Restriction(int restrictedUserID, String restrictedType, int executorID, String reason, Timestamp dueTime) {
        this.restrictedUserId = restrictedUserID;
        this.restrictedType = restrictedType;
        this.executorId = executorID;
        this.reason = reason;
        this.dueTime = dueTime;
    }

//    public User getRestrictedUser() throws SQLException {
//        if (restrictedUser == null)
//            restrictedUser = UserRepository.getInstance().getById(restrictedUserId);
//        return restrictedUser;
//    }
//
//    public void setRestrictedUser(User restrictedUser) {
//        this.restrictedUserId = restrictedUser.getId();
//        this.restrictedUser = restrictedUser;
//    }

    public String getRestrictedType() {
        return restrictedType;
    }

    public void setRestrictedType(String restrictedType) {
        this.restrictedType = restrictedType;
    }

//    public User getExecutor() throws SQLException {
//        if (executor == null)
//            executor = UserRepository.getInstance().getById(executorId);
//        return executor;
//    }
//
//    public void setExecutor(User executor) {
//        this.executorId = executor.getId();
//        this.executor = executor;
//    }

    public int getRestrictedUserId() {
        return restrictedUserId;
    }

    public void setRestrictedUserId(int restrictedUserId) {
        this.restrictedUserId = restrictedUserId;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
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

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("restrictedUserId", restrictedUserId);
        json.put("restrictedType", restrictedType);
        json.put("executorId", executorId);
        json.put("reason", reason);
        json.put("dueTime", dueTime);
        return json;
    }
}
