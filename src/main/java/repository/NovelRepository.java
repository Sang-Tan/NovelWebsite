package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Novel;
import model.intermediate.NovelGenre;
import repository.intermediate.NovelGenreRepository;
import service.URLSlugification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NovelRepository extends BaseRepository<Novel> {
    private static NovelRepository instance;

    public static NovelRepository getInstance() {
        if (instance == null) {
            instance = new NovelRepository();
        }
        return instance;
    }

    @Override
    protected Novel createEmpty() {
        return new Novel();
    }

    public Novel getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public List<Novel> getByConditionString(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT * FROM %s AS novel1 WHERE %s", getTableName(), condition);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return mapObjects(records);
    }

    public long countNovels(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT COUNT(id) FROM %s WHERE %s", getTableName(), condition);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        for (SqlRecord record : records) {
            return (long) record.get("COUNT(id)");
        }
        return 0;
    }


    public Novel createNovel(String novelName, String summary,
                             String status, String imageURI, int ownerID) {
        Novel novel = new Novel();
        novel.setName(novelName);
        novel.setSummary(summary);
        novel.setStatus(status);
        novel.setImage(imageURI);
        novel.setOwnerID(ownerID);
        return novel;
    }

    public void changeNovelGenres(int novelID, int[] genres) throws SQLException {
        //delete all genres of this novel
        NovelGenreRepository.getInstance().deleteByNovelId(novelID);

        ArrayList<NovelGenre> novelGenres = new ArrayList<>();
        for (Integer genre : genres) {
            NovelGenre novelGenre = new NovelGenre();
            novelGenre.setNovelId(novelID);
            novelGenre.setGenreId(genre);
            novelGenres.add(novelGenre);
        }
        NovelGenreRepository.getInstance().insertBatch(novelGenres);
    }

    public Collection<Novel> getNovelsByOwnerID(int ownerID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE owner = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ownerID));
        return mapObjects(records);
    }

    public Collection<Novel> getAllPendingNovel(String approvalStatus) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE approval_status = ?", getTableName());
        sql += "ORDER BY created_at DESC";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(approvalStatus));
        return mapObjects(records);
    }

    public Novel getByVolumeID(int volumeID) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE id = (SELECT novel_id FROM volumes WHERE id = ?)", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(volumeID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public Novel getByChapterID(int chapterID) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE id = (SELECT novel_id " +
                "FROM volumes WHERE id = " +
                "(SELECT volume_id FROM chapters WHERE id = ?))", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(chapterID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public Collection<Novel> getFavoriteNovelsByUserID(int userID) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE id IN (SELECT novel_id FROM novel_favourite WHERE user_id = ?)", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(userID));
        return mapObjects(records);
    }

    public String generatePathComponent(int novelID) throws SQLException {
        Novel novel = getById(novelID);
        return URLSlugification.sluging(novel.getId() + " " + novel.getName());
    }

    public List<Novel> getInModeratingNovelsQueue() throws SQLException {
        String sql = "SELECT * FROM novels " +
                "WHERE (approval_status = 'pending') " +
                "OR (approval_status = 'approved' AND " +
                "EXISTS( SELECT novel_id FROM novel_changes " +
                "WHERE novel_id = novels.id)) " +
                "ORDER BY updated_at ASC";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql);
        return mapObjects(records);
    }

    public List<Novel> getAllNovelReport(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id IN (SELECT novel_id FROM novel_report GROUP BY novel_id ORDER BY min(report_time)) LIMIT ? OFFSET ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return mapObjects(records);
    }

    public boolean isReportExist(int novelID, int reporterId) throws SQLException {
        String sql = String.format("SELECT * FROM novel_report WHERE novel_id = ? AND reporter_id = ?");
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelID, reporterId));
        return records.size() > 0;
    }

    public void addViewCount(int novelId, int viewCount) throws SQLException {
        Novel novel = getById(novelId);
        if (novel == null) return;

        String sql = String.format("UPDATE %s SET view_count = view_count + ? WHERE id = ?", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(viewCount, novelId));
    }

    public boolean isNovelHasAnyApprovedChapter(int novelId) throws SQLException {
        String sql = String.format("SELECT COUNT(id) as chapter_count FROM chapters " +
                "WHERE approval_status = 'approved' " +
                "AND volume_id IN " +
                "(SELECT id FROM volumes " +
                "WHERE novel_id = ?)");

        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        if (records.size() > 0) {
            SqlRecord record = records.get(0);
            //if novel has only 1 chapter, that chapter is virtual chapter
            return (long) record.get("chapter_count") > 1;
        }
        return false;
    }
}
