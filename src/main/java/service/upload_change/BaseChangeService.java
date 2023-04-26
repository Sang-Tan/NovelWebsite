package service.upload_change;

import model.INovelContent;
import model.temporary.INovelContentChange;

import java.sql.SQLException;

public abstract class BaseChangeService<T1 extends INovelContent, T2 extends INovelContentChange> {
    private final String APPROVAL_STATUS_APPROVED;
    private final String APPROVAL_STATUS_REJECTED;
    private final String APPROVAL_STATUS_PENDING;

    protected BaseChangeService(String statusApproved, String statusRejected, String statusPending) {
        APPROVAL_STATUS_APPROVED = statusApproved;
        APPROVAL_STATUS_REJECTED = statusRejected;
        APPROVAL_STATUS_PENDING = statusPending;
    }

    protected abstract String getApprovalStatus(int id) throws SQLException;

    public abstract T2 getChangeByResourceId(int id) throws SQLException;

    public abstract void createChange(T1 oldResourceInfo, T1 newResourceInfo) throws SQLException;

    protected ContentChangeType getChangeTypeWhenApproved(int id) throws SQLException {
        T2 change = getChangeByResourceId(id);
        if (change == null) {
            return ContentChangeType.NONE;
        }
        return ContentChangeType.UPDATE;
    }

    public ContentChangeType getChangeType(int id) throws SQLException {
        String approvalStatus = getApprovalStatus(id);
        if (approvalStatus.equals(APPROVAL_STATUS_APPROVED)) {
            return getChangeTypeWhenApproved(id);
        } else if (approvalStatus.equals(APPROVAL_STATUS_REJECTED)) {
            return ContentChangeType.NONE;
        } else if (approvalStatus.equals(APPROVAL_STATUS_PENDING)) {
            return ContentChangeType.NEW;
        } else {
            throw new SQLException("Invalid approval status");
        }
    }

    public boolean canUpdateContent(int id) throws SQLException {
        T2 change = getChangeByResourceId(id);
        String approvalStatus = getApprovalStatus(id);
        if (change == null && !approvalStatus.equals(APPROVAL_STATUS_PENDING)) {
            return true;
        }
        return false;
    }

    public boolean canCreateChange(int id) throws SQLException {
        T2 change = getChangeByResourceId(id);
        String approvalStatus = getApprovalStatus(id);
        if (change == null && approvalStatus.equals(APPROVAL_STATUS_APPROVED)) {
            return true;
        }
        return false;
    }
}
