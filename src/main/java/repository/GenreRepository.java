package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenreRepository extends BaseRepository<Genre> {

    private static GenreRepository instance;

    public static GenreRepository getInstance() {
        if (instance == null) {
            instance = new GenreRepository();
        }
        return instance;
    }

    @Override
    protected Genre createEmpty() {
        return new Genre();
    }

    public Genre findById(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(id));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public List<Genre> getByListId(List<Object> novelId) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id IN (";
        for (int i = 0; i < novelId.size(); i++) {
            sql += "?,";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ")";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, novelId);
        return mapObjects(records);
    }

    public List<Genre> getByIds(int[] ids) throws SQLException {
        ArrayList<Genre> genres = new ArrayList<>();
        for (int id : ids) {
            genres.add(findById(id));
        }
        return genres;
    }

    public Set<Genre> getByNovelId(int novelId) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() +
                " WHERE id IN " +
                "(SELECT genre_id FROM novel_genre WHERE novel_id = ?)";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        return new HashSet<>(mapObjects(records));
    }
}
