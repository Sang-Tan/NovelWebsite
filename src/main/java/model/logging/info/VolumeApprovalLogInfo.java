package model.logging.info;

import model.User;
import model.logging.VolumeApprovalLog;
import repository.UserRepository;

import java.sql.SQLException;
import java.sql.Timestamp;

public class VolumeApprovalLogInfo implements IApprovalLogInfo<VolumeApprovalLog> {

    private final VolumeApprovalLog volumeApprovalLog;

    private final User moderator;

    public VolumeApprovalLogInfo(VolumeApprovalLog volumeApprovalLog) throws SQLException {
        this.volumeApprovalLog = volumeApprovalLog;
        Integer moderatorId = volumeApprovalLog.getModeratorId();
        moderator = UserRepository.getInstance().getById(moderatorId);
    }

    @Override
    public String getContent() {
        return volumeApprovalLog.getContent();
    }

    @Override
    public Timestamp getCreatedTime() {
        return volumeApprovalLog.getCreatedTime();
    }

    @Override
    public User getModerator() {
        return moderator;
    }
}
