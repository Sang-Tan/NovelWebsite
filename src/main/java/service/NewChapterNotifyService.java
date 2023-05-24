package service;

import model.Chapter;
import model.Novel;
import model.NovelReport;
import model.User;
import model.intermediate.NovelFavourite;
import repository.NovelFavouriteRepository;
import repository.NovelRepository;
import service.report.NovelReportService;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class NewChapterNotifyService {
    private static NewChapterNotifyService instance;

    public static NewChapterNotifyService getInstance() {
        if (instance == null) {
            synchronized (NewChapterNotifyService.class) {
                if (instance == null) {
                    instance = new NewChapterNotifyService();
                }
            }
        }
        return instance;
    }

    public void notifyNewChapterToFollowers(Chapter chapter) throws SQLException {
        Novel novel = NovelRepository.getInstance().getByChapterID(chapter.getId());
        String novelName = novel.getName();
        String notification = String.format("Tiểu thuyết %s vừa có chương mới: %s", novelName, chapter.getName());
        String notificationURL = String.format("/doc-tieu-thuyet/%s/%s", novel.getId(), chapter.getId());

        List<NovelFavourite> novelFavourites = NovelFavouriteRepository.getInstance().getByNovelId(novel.getId());
        for (NovelFavourite novelFavourite : novelFavourites) {
            Integer userId = novelFavourite.getUserId();
            NotificationService.addNotification(userId, notification, notificationURL);
        }
    }
}
