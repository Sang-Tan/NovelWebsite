package service.logging;

import model.Novel;
import model.logging.NovelApprovalLog;
import repository.logging.NovelApprovalLogRepository;

import java.sql.SQLException;
import java.util.List;

public class NovelApprovalLoggingService extends ApprovalLoggingService<Novel, NovelApprovalLog> {

    private static NovelApprovalLoggingService instance;

    public static NovelApprovalLoggingService getInstance() {
        if (instance == null) {
            synchronized (NovelApprovalLoggingService.class) {
                if (instance == null) {
                    instance = new NovelApprovalLoggingService();
                }
            }
        }
        return instance;
    }

    private NovelApprovalLoggingService() {
        super();
    }

    @Override
    public List<NovelApprovalLog> getLogsByResourceId(Integer novelId) throws SQLException {
        return NovelApprovalLogRepository.getInstance().getOrderedLogsByNovelId(novelId, true);
    }

    @Override
    public NovelApprovalLog saveLog(NovelApprovalLog log) throws SQLException {
        return NovelApprovalLogRepository.getInstance().insert(log);
    }
}
