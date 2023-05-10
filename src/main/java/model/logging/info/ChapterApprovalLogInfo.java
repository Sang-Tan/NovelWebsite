package model.logging.info;

import model.User;
import model.logging.ChapterApprovalLog;
import repository.UserRepository;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ChapterApprovalLogInfo implements IApprovalLogInfo<ChapterApprovalLog> {
    private final ChapterApprovalLog chapterApprovalLog;

    private final User moderator;

    public ChapterApprovalLogInfo(ChapterApprovalLog chapterApprovalLog) throws SQLException {
        this.chapterApprovalLog = chapterApprovalLog;
        Integer moderatorId = chapterApprovalLog.getModeratorId();
        moderator = UserRepository.getInstance().getById(moderatorId);
    }


    @Override
    public String getContent() {
        return chapterApprovalLog.getContent();
    }

    @Override
    public Timestamp getCreatedTime() {
        return chapterApprovalLog.getCreatedTime();
    }

    @Override
    public User getModerator() {
        return moderator;
    }
}
