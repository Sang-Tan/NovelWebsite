package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Comment;
import model.Novel;
import model.intermediate.NovelGenre;
import repository.intermediate.NovelGenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        String sql = String.format("SELECT * FROM %s WHERE %s", getTableName(), condition);
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

    public List<Comment> getComments(int id) throws SQLException {
            return CommentRepository.getInstance().getByNovelId(id);
    }
    public Collection<Novel> getFavoriteNovelsByUserID(int userID) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE id IN (SELECT novel_id FROM novel_favourite WHERE user_id = ?)", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(userID));
        return mapObjects(records);
    }
}
