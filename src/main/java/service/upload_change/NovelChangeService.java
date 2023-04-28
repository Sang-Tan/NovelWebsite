package service.upload_change;

import model.Novel;
import model.temporary.NovelChange;
import repository.NovelRepository;
import repository.temporary.NovelChangeRepository;
import service.upload.FileMapper;
import service.upload_change.base.BaseChangeService;

import java.io.IOException;
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

        if (!oldNovelInfo.getName().equals(newNovelInfo.getName())) {
            novelChange.setName(newNovelInfo.getName());
        }

        if (!oldNovelInfo.getImage().equals(newNovelInfo.getImage())) {
            novelChange.setImage(newNovelInfo.getImage());
        }

        if (!oldNovelInfo.getSummary().equals(newNovelInfo.getSummary())) {
            novelChange.setSummary(newNovelInfo.getSummary());
        }

        if (novelChange.getName() != null || novelChange.getImage() != null || novelChange.getSummary() != null) {
            NovelChangeRepository.getInstance().insert(novelChange);
        }

    }

    @Override
    protected void mergeChange(int novelId) throws SQLException, IOException {
        NovelChange novelChange = NovelChangeRepository.getInstance().getByNovelId(novelId);
        Novel novel = NovelRepository.getInstance().getById(novelId);

        if (novelChange.getName() != null) {
            novel.setName(novelChange.getName());
        }

        if (novelChange.getImage() != null) {
            FileMapper oldImage = FileMapper.mapURI(novel.getImage());
            FileMapper newImage = FileMapper.mapURI(novelChange.getImage());
            oldImage.copyFrom(newImage);
        }

        if (novelChange.getSummary() != null) {
            novel.setSummary(novelChange.getSummary());
        }

        NovelRepository.getInstance().update(novel);
        deleteNovelChange(novelChange);

    }

    @Override
    protected void approveNewResource(int novelId) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(novelId);
        novel.setApprovalStatus(Novel.APPROVE_STATUS_APPROVED);
        NovelRepository.getInstance().update(novel);
    }

    @Override
    protected void rejectAndDeleteChange(int id) throws SQLException {

    }

    @Override
    protected void rejectNewResource(int id) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(id);
        novel.setApprovalStatus(Novel.APPROVE_STATUS_REJECTED);
        NovelRepository.getInstance().update(novel);
    }

    protected void deleteNovelChange(int novelId) throws SQLException {
        NovelChange novelChange = NovelChangeRepository.getInstance().getByNovelId(novelId);
        deleteNovelChange(novelChange);
    }

    protected void deleteNovelChange(NovelChange novelChange) throws SQLException {
        String imageURI = novelChange.getImage();
        //delete image if exist
        if (imageURI != null) {
            FileMapper fileMapper = FileMapper.mapURI(imageURI);
            fileMapper.delete();
        }

        NovelChangeRepository.getInstance().delete(novelChange);
    }

}
