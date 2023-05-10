package model.logging.info;

import model.User;
import model.logging.NovelApprovalLog;
import repository.UserRepository;
import service.validator.UserService;

import java.sql.SQLException;
import java.sql.Timestamp;

public class NovelApprovalLogInfo implements IApprovalLogInfo<NovelApprovalLog> {

    private final NovelApprovalLog novelApprovalLog;

    private final User moderator;

    public NovelApprovalLogInfo(NovelApprovalLog novelApprovalLog) throws SQLException {
        this.novelApprovalLog = novelApprovalLog;
        int moderatorId = novelApprovalLog.getModeratorId();
        moderator = UserRepository.getInstance().getById(moderatorId);
    }

    @Override
    public String getContent() {
        return novelApprovalLog.getContent();
    }

    @Override
    public Timestamp getCreatedTime() {
        return novelApprovalLog.getCreatedTime();
    }

    @Override
    public User getModerator() {
        return moderator;
    }
}
