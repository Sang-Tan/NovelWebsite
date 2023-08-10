package service.upload_change;

import model.Chapter;
import model.temporary.ChapterChange;
import repository.ChapterRepository;
import repository.temporary.ChapterChangeRepository;
import service.NewChapterNotifyService;
import service.upload_change.base.BaseChangeService;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ChapterChangeService extends BaseChangeService<Chapter, ChapterChange> {
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
        return ChapterChangeRepository.getInstance().getByChapterId(id);
    }

    @Override
    public void createChange(Chapter oldChapterInfo, Chapter newChapterInfo) throws SQLException {
        ChapterChange chapterChange = new ChapterChange();
        chapterChange.setChapterId(oldChapterInfo.getId());

        if (!oldChapterInfo.getName().equals(newChapterInfo.getName())) {
            chapterChange.setName(newChapterInfo.getName());
        }

        if (!oldChapterInfo.getContent().equals(newChapterInfo.getContent())) {
            chapterChange.setContent(newChapterInfo.getContent());
        }

        if (chapterChange.getName() != null || chapterChange.getContent() != null) {
            ChapterChangeRepository.getInstance().insert(chapterChange);
        }
    }

    @Override
    protected void mergeChange(int chapterId) throws SQLException {
        Chapter chapter = ChapterRepository.getInstance().getById(chapterId);
        ChapterChange chapterChange = ChapterChangeRepository.getInstance().getByChapterId(chapterId);

        if (chapterChange.getName() != null) {
            chapter.setName(chapterChange.getName());
        }

        if (chapterChange.getContent() != null) {
            chapter.setContent(chapterChange.getContent());
        }

        ChapterRepository.getInstance().update(chapter);
        ChapterChangeRepository.getInstance().delete(chapterChange);
    }

    @Override
    protected void approveNewResource(int chapterId) throws SQLException {
        Chapter chapter = ChapterRepository.getInstance().getById(chapterId);
        chapter.setApprovalStatus(Chapter.APPROVE_STATUS_APPROVED);
        chapter.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        ChapterRepository.getInstance().update(chapter);
        NewChapterNotifyService.getInstance().notifyNewChapterToFollowers(chapter);
    }

    @Override
    protected void rejectAndDeleteChange(int chapterId) throws SQLException {
        ChapterChangeRepository.getInstance().deleteByChapterId(chapterId);
    }

    @Override
    protected void rejectNewResource(int chapterId) throws SQLException {
        Chapter chapter = ChapterRepository.getInstance().getById(chapterId);
        chapter.setApprovalStatus(Chapter.APPROVE_STATUS_REJECTED);
        ChapterRepository.getInstance().update(chapter);
    }
}
