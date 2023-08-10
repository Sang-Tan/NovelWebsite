package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.intermediate.NovelFavourite;

import java.sql.SQLException;
import java.util.List;

public class NovelFavouriteRepository extends BaseRepository<NovelFavourite> {

    private static NovelFavouriteRepository instance;

    public static NovelFavouriteRepository getInstance() {
        if (instance == null) {
            instance = new NovelFavouriteRepository();
        }
        return instance;
    }

    @Override
    protected NovelFavourite createEmpty() {
        return new NovelFavourite();
    }

    public List<NovelFavourite> getByUserId(int userId) throws Exception {
        String sql = String.format("SELECT * FROM %s WHERE user_id = ?", getTableName());
        return mapObjects(MySQLdb.getInstance().select(sql, List.of(userId)));
    }

    public int getFavouriteCount(int novelId) throws Exception {
        String sql = String.format("SELECT COUNT(*) as favourite_count FROM %s WHERE novel_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        if (records.size() == 0) {
            return 0;
        }

        Object value = records.get(0).get("favourite_count");
        if (value == null) {
            return 0;
        }

        return Integer.parseInt(value.toString());
    }

    public boolean isFavourite(int userId, int novelId) throws Exception {
        String sql = String.format("SELECT * FROM %s WHERE user_id = ? AND novel_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(userId, novelId));
        return records.size() > 0;
    }

    public List<NovelFavourite> getByNovelId(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ?", getTableName());
        return mapObjects(MySQLdb.getInstance().select(sql, List.of(novelId)));
    }

}
