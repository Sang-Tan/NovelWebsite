package service;


import model.Novel;
import repository.NovelRepository;
import repository.ViewInNovelRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ViewService {
    public static ViewService instance = null;
    // time period to update view count in database
    private static final int PERIOD_SECONDS_TO_UPDATE_DB = 60; // 1 minute
    // time delay to start update view count in database
    private static final int EXPIRED_DAYS = 30;

    // time period to collect garbage records in view_in_novel table which have date_view < current_date - expired day
    private static final int PERIOD_SECONDS_TO_DELETE_EXPIRED_RECORD = 60 * 60 * 24;// 1 day
    // map cache to store view count of each novel
    private final Map<Integer, Set<String>> chapterViewCache;

    public static ViewService getInstance() {
        if (instance == null)
            synchronized (ViewService.class) {
                instance = new ViewService();
            }
        return instance;
    }

    private ViewService() {
        chapterViewCache = new HashMap<>();
        // schedule tasks to run at a specified time or with a specified delay, use for running periodic tasks
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
        exec.scheduleAtFixedRate(() -> {
            try {
                updateDb();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, PERIOD_SECONDS_TO_UPDATE_DB, PERIOD_SECONDS_TO_UPDATE_DB, java.util.concurrent.TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(() -> {
            try {
                ViewInNovelRepository.getInstance().deleteExpiredViewInNovel(EXPIRED_DAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, PERIOD_SECONDS_TO_DELETE_EXPIRED_RECORD, java.util.concurrent.TimeUnit.SECONDS);
    }


    /**
     * Add amount of view chapter to ChapterViewInThread map which store view count of each chapter
     *
     * @param chapterId
     */
    public synchronized void addViewToCache(int chapterId, String sessionId, int viewCount) {
        if (chapterViewCache.containsKey(chapterId)) {
            chapterViewCache.get(chapterId).add(sessionId);
        } else {
            Set<String> sessionIds = new HashSet<>();
            sessionIds.add(sessionId);
            chapterViewCache.put(chapterId, sessionIds);
        }
    }

    /**
     * View count of each chapter is stored in a map, this function will add view count of each chapter in database
     * Call this function when thread is end
     */
    synchronized private void updateDb() throws SQLException {
        for (Map.Entry<Integer, Set<String>> entry : chapterViewCache.entrySet()) {
            int chapterId = entry.getKey();
            int viewCount = entry.getValue().size();
            Date currentDate = Date.valueOf(LocalDate.now());

            int belongingNovelId = NovelRepository.getInstance().getByChapterID(chapterId).getId();
            ViewInNovelRepository.getInstance().addViewCount(belongingNovelId, currentDate, viewCount);
            NovelRepository.getInstance().addViewCount(belongingNovelId, viewCount);
        }
        chapterViewCache.clear();
    }

    private List<Novel> getTopViewNovels(int numOfNovels, Date startDay, Date endDate) throws SQLException {
        return ViewInNovelRepository.getInstance().getTopViewNovels(numOfNovels, startDay, endDate);
    }

    public List<Novel> getTopViewRecentDayNovels(int numOfNovels) throws SQLException {
        return getTopViewNovels(numOfNovels, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()));
    }

    public List<Novel> getTopViewRecentWeekNovels(int numOfNovels) throws SQLException {
        return getTopViewNovels(numOfNovels, Date.valueOf(LocalDate.now().minusDays(6)), Date.valueOf(LocalDate.now()));
    }

    public List<Novel> getTopViewRecentMonthNovels(int numOfNovels) throws SQLException {
        return getTopViewNovels(numOfNovels, Date.valueOf(LocalDate.now().minusDays(29)), Date.valueOf(LocalDate.now()));
    }

}
