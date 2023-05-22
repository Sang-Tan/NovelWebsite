package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Novel;
import model.ViewInNovel;

import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ViewInNovelRepository extends BaseRepository<ViewInNovel> {
    public static ViewInNovelRepository instance = null;

    public static ViewInNovelRepository getInstance(){
        if(instance == null){
            instance = new ViewInNovelRepository();
        }
        return instance;
    }
    public void addViewCount(int novelId, Date date, int viewCount) throws SQLException {
        ViewInNovel viewInNovel = getInDateViewInNovel(novelId, date);
        if(viewInNovel == null)
        {
            viewInNovel = createEmpty();
            viewInNovel.setNovelId(novelId);
            viewInNovel.setDateView(date);
            viewInNovel.setViewCount(viewCount);
            insert(viewInNovel);
            return;
        }

        String sql = String.format("UPDATE %s SET view_count = view_count + ? WHERE novel_id = ? and date_view = ?",getTableName() );
        Date currentDate = new Date(System.currentTimeMillis());
        MySQLdb.getInstance().execute(sql, List.of(viewCount, novelId, currentDate));
    }
    public ViewInNovel getInDateViewInNovel(int novelId, Date date) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ? and date_view = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId, date));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }
    public ViewInNovel deleteExpiredViewInNovel(int expiredDays) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE date_view < DATE_SUB(CURDATE(), INTERVAL ? DAY)", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(expiredDays));
        return null;
    }

    @Override
    protected ViewInNovel createEmpty() {
        return new ViewInNovel();
    }

    @Override
    public ViewInNovel insert(ViewInNovel object) throws SQLException {
        //check valid
        int novelId = object.getNovelId();
        if (NovelRepository.getInstance().getById(novelId) == null) {
            throw new SQLException(String.format("novel id %d not found", novelId));
        }
        if(ViewInNovelRepository.getInstance().getInDateViewInNovel(object.getNovelId(), object.getDateView()) != null)
        {
            throw new SQLException(String.format("record has novelId %d in date %s in view_in_novel table already exist", novelId, object.getDateView().toString()));
        }

        return super.insert(object);
    }
    public List<Integer> getTopViewNovelIds(int numOfNovels, Date startDay, Date endDate) throws SQLException {
        String sql = String.format("SELECT novel_id FROM %s WHERE date_view >= ? and date_view <= ? GROUP BY novel_id ORDER BY SUM(view_count) DESC LIMIT ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(startDay, endDate, numOfNovels));

        List<Integer> novelIds = new ArrayList<>();
        for (SqlRecord record : records) {
            novelIds.add((Integer) record.get("novel_id"));
        }
        return novelIds;
    }
    public List<Novel> getTopViewNovels(int numOfNovels, Date startDay, Date endDate) throws SQLException {
        List<Integer> novelIds = getTopViewNovelIds(numOfNovels, startDay, endDate);
        List<Novel> novels = new ArrayList<>();
        for (Integer novelId : novelIds) {
            novels.add(NovelRepository.getInstance().getById(novelId));
        }
        return novels;
    }

}
