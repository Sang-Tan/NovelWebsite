package service.upload_change;

import model.Chapter;
import model.temporary.ChapterChange;
import repository.ChapterRepository;

import java.sql.SQLException;

public class ChapterChangeService extends BaseChangeService<ChapterChange> {
    private static ChapterChangeService instance;

    public static ChapterChangeService getInstance() {
        if (instance == null) {
            synchronized (ChapterChangeService.class) {
                if (instance == null) {
                    instance = new ChapterChangeService(Chapter.APPROVE_STATUS_APPROVED,
                            Chapter.APPROVE_STATUS_REJECTED, Chapter.APPROVE_STATUS_PENDING);
                }
            }
        }
        return instance;
    }

    protected ChapterChangeService(String statusApproved, String statusRejected, String statusPending) {
        super(statusApproved, statusRejected, statusPending);
    }

    @Override
    protected String getApprovalStatus(int id) throws SQLException {
        Chapter chapter = ChapterRepository.getInstance().getById(id);
        return chapter.getApprovalStatus();
    }

    @Override
    public ChapterChange getChangeByResourceId(int id) throws SQLException {
        return null;
    }
}
