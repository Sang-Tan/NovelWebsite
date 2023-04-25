package service.upload_change;

import model.Novel;
import model.temporary.NovelChange;
import repository.NovelRepository;
import repository.temporary.NovelChangeRepository;

import java.sql.SQLException;

public class NovelChangeService extends BaseChangeService<NovelChange> {
    private static NovelChangeService instance;

    public static NovelChangeService getInstance() {
        if (instance == null) {
            synchronized (NovelChangeService.class) {
                if (instance == null) {
                    instance = new NovelChangeService(Novel.APPROVE_STATUS_APPROVED,
                            Novel.APPROVE_STATUS_REJECTED, Novel.APPROVE_STATUS_PENDING);
                }
            }
        }
        return instance;
    }

    protected NovelChangeService(String statusApproved, String statusRejected, String statusPending) {
        super(statusApproved, statusRejected, statusPending);
    }

    @Override
    protected String getApprovalStatus(int id) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(id);
        return novel.getApprovalStatus();
    }

    @Override
    public NovelChange getChangeByResourceId(int id) throws SQLException {
        return NovelChangeRepository.getInstance().getByNovelId(id);
    }
}