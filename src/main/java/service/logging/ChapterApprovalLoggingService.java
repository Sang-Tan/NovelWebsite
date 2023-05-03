package service.logging;

import model.Chapter;
import model.logging.ChapterApprovalLog;
import repository.logging.ChapterApprovalLogRepository;

import java.sql.SQLException;
import java.util.List;

public class ChapterApprovalLoggingService extends ApprovalLoggingService<Chapter, ChapterApprovalLog> {

    private static ChapterApprovalLoggingService instance;

    public static ChapterApprovalLoggingService getInstance() {
        if (instance == null) {
            synchronized (ChapterApprovalLoggingService.class) {
                if (instance == null) {
                    instance = new ChapterApprovalLoggingService();
                }
            }
        }
        return instance;
    }

    private ChapterApprovalLoggingService() {
        super();
    }

    @Override
    public List<ChapterApprovalLog> getLogsByResourceId(Integer chapterId) throws SQLException {
        return ChapterApprovalLogRepository.getInstance().getOrderedLogsByChapterId(chapterId, true);
    }

    @Override
    public ChapterApprovalLog saveLog(ChapterApprovalLog log) throws SQLException {
        return ChapterApprovalLogRepository.getInstance().insert(log);
    }
}
