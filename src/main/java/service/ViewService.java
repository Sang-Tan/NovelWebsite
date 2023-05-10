package service;


import model.Novel;
import repository.NovelRepository;
import repository.ViewInNovelRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ViewService {
    public static ViewService instance = null;
    // schedule tasks to run at a specified time or with a specified delay, use for running periodic tasks
    private static ScheduledExecutorService exec;
    // time period to update view count in database
    private static final int PERIOD_SECONDS_TO_UPDATE_DB = 30;
    // time delay to start update view count in database
    private static final int EXPIRED_DAYS = 30;
    private static final int INITIAL_SECOND_DELAY_TO_UPDATE_DB = 30;
    // time period to collect garbage records in view_in_novel table which have date_view < current_date - expired day
    private static final int PERIOD_SECONDS_TO_DELETE_EXPIRED_RECORD = 60 * 60 * 24;// 1 day
    // map cache to store view count of each novel
    volatile Map<Integer, Integer> novelViewCache = new HashMap<Integer, Integer>();

    public static ViewService getInstance() {
        if (instance == null)
            synchronized (ViewService.class)
            {
                instance = new ViewService();
            }
        return instance;
    }

    private ViewService() {
        exec = Executors.newScheduledThreadPool(2);
        exec.scheduleAtFixedRate(() -> {
            try {
                updateDb();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, INITIAL_SECOND_DELAY_TO_UPDATE_DB, PERIOD_SECONDS_TO_UPDATE_DB, java.util.concurrent.TimeUnit.SECONDS);
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
     * @param novelId
     */
    public synchronized void addViewToCache(int novelId, int viewCount) {
        if (novelViewCache.containsKey(novelId)) {
            novelViewCache.put(novelId, novelViewCache.get(novelId) + viewCount);
        } else {
            novelViewCache.put(novelId, viewCount);
        }
    }

    /**
     * View count of each chapter is stored in a map, this function will add view count of each chapter in database
     * Call this function when thread is end
     */
    synchronized private void updateDb() throws SQLException {
        for (Map.Entry<Integer, Integer> entry : novelViewCache.entrySet()) {
            int novelId = entry.getKey();
            int viewCount = entry.getValue();
            Date currentDate = Date.valueOf(LocalDate.now());
            ViewInNovelRepository.getInstance().addViewCount(novelId, currentDate, viewCount);
            NovelRepository.getInstance().addViewCount(novelId, viewCount);
        }
        novelViewCache.clear();
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
