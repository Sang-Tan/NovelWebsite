package service.upload_change;

import model.Novel;
import model.temporary.NovelChange;
import repository.NovelRepository;
import repository.temporary.NovelChangeRepository;

import java.sql.SQLException;

public class NovelChangeService extends BaseChangeService<Novel, NovelChange> {
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

    @Override
    public void createChange(Novel oldNovelInfo, Novel newNovelInfo) throws SQLException {
        NovelChange novelChange = new NovelChange();
        novelChange.setNovelId(oldNovelInfo.getId());

        boolean makeChange = false;

        if (!oldNovelInfo.getName().equals(newNovelInfo.getName())) {
            novelChange.setName(newNovelInfo.getName());
            makeChange = true;
        }

        if (!oldNovelInfo.getImage().equals(newNovelInfo.getImage())) {
            novelChange.setImage(newNovelInfo.getImage());
            makeChange = true;
        }

        if (!oldNovelInfo.getSummary().equals(newNovelInfo.getSummary())) {
            novelChange.setSummary(newNovelInfo.getSummary());
            makeChange = true;
        }

        if (makeChange) {
            NovelChangeRepository.getInstance().insert(novelChange);
        }

    }

}
