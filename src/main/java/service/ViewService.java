package service;


import model.Novel;
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
    private ScheduledExecutorService exec;
    // time period to update view count in database
    private static final int PERIOD_IN_SECONDS = 30;
    // time delay to start update view count in database
    private static final int INITIAL_DELAY_IN_SECONDS = 30;
    Map<Integer, Integer> ChapterViewCache = new HashMap<Integer, Integer>();

    public static ViewService getInstance() {
        synchronized (ViewService.class) {
            if (instance == null) {
                instance = new ViewService();
            }
            return instance;
        }
    }

    private ViewService() {
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            try {
                updateDb();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, INITIAL_DELAY_IN_SECONDS, PERIOD_IN_SECONDS, java.util.concurrent.TimeUnit.SECONDS);
    }


    /**
     * Add amount of view chapter to ChapterViewInThread map which store view count of each chapter
     *
     * @param chapterId
     */
    public synchronized void addViewToMap(int chapterId, int viewCount) {
        if (ChapterViewCache.containsKey(chapterId)) {
            ChapterViewCache.put(chapterId, ChapterViewCache.get(chapterId) + viewCount);
        } else {
            ChapterViewCache.put(chapterId, viewCount);
        }
    }

    /**
     * View count of each chapter is stored in a map, this function will add view count of each chapter in database
     * Call this function when thread is end
     */
    synchronized private void updateDb() throws SQLException {
        for (Map.Entry<Integer, Integer> entry : ChapterViewCache.entrySet()) {
            int ChapterId = entry.getKey();
            int viewCount = entry.getValue();
            Date currentDate = Date.valueOf(LocalDate.now());
            ViewInNovelRepository.getInstance().addViewCount(ChapterId, currentDate, viewCount);
        }
        ChapterViewCache.clear();
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

    public List<Novel> getTopViewRecentYearNovels(int numOfNovels) throws SQLException {
        return getTopViewNovels(numOfNovels, Date.valueOf(LocalDate.now().minusDays(364)), Date.valueOf(LocalDate.now()));
    }

    public List<Novel> getTopViewAllTimeNovels(int numOfNovels) throws SQLException {
        Date startDate = Date.valueOf("0001-01-01");// earliest date in database
        return getTopViewNovels(numOfNovels, startDate, Date.valueOf(LocalDate.now()));
    }
}
