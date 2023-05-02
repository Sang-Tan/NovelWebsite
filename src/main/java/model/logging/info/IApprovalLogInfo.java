package model.logging.info;

import model.User;
import model.logging.IApprovalLog;

import java.sql.Timestamp;

public interface IApprovalLogInfo<T extends IApprovalLog> {
    String getContent();

    Timestamp getCreatedTime();

    User getModerator();
}
