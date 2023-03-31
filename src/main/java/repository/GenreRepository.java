package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    @Override
    protected Genre createDefault() {
        return new Genre();
    }

    public Genre findByName(String name) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE name = ?";
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{name});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }

    public Genre findById(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{id});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }
}
