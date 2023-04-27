package service.upload_change.base;

import model.INovelContent;
import model.temporary.INovelContentChange;
import service.upload_change.metadata.ContentChangeType;

import java.io.IOException;
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

    public boolean waitingForModeration(int id) throws SQLException {
        T2 change = getChangeByResourceId(id);
        String approvalStatus = getApprovalStatus(id);
        if (change == null && !approvalStatus.equals(APPROVAL_STATUS_PENDING)) {
            return false;
        }
        if (change != null && !approvalStatus.equals(APPROVAL_STATUS_APPROVED)) {
            throw new RuntimeException("Invalid approval state");
        }
        return true;
    }

    public boolean canCreateChange(int id) throws SQLException {
        T2 change = getChangeByResourceId(id);
        String approvalStatus = getApprovalStatus(id);
        if (change == null && approvalStatus.equals(APPROVAL_STATUS_APPROVED)) {
            return true;
        }
        return false;
    }

    public void approveChange(int id) throws SQLException, IOException {
        String approvalStatus = getApprovalStatus(id);
        if (!waitingForModeration(id)) {
            throw new RuntimeException("Cannot approve change in current state(not in moderation state)");
        }

        if (approvalStatus.equals(APPROVAL_STATUS_APPROVED)) {
            mergeChange(id);
        } else if (approvalStatus.equals(APPROVAL_STATUS_PENDING)) {
            approveNewResource(id);
        }
    }

    protected abstract void mergeChange(int id) throws SQLException, IOException;

    protected abstract void approveNewResource(int id) throws SQLException;

    public void rejectChange(int id) throws SQLException {
        if (!waitingForModeration(id)) {
            throw new RuntimeException("Cannot reject change in current state(not in moderation state)");
        }
        String approvalStatus = getApprovalStatus(id);

        if (approvalStatus.equals(APPROVAL_STATUS_APPROVED)) {
            rejectAndDeleteChange(id);
        } else if (approvalStatus.equals(APPROVAL_STATUS_PENDING)) {
            rejectNewResource(id);
        }
    }

    protected abstract void rejectAndDeleteChange(int id) throws SQLException;

    protected abstract void rejectNewResource(int id) throws SQLException;
}
