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

    protected GenreRepository() {
        super("genres", new String[]{"id"});
    }

    @Override
    protected Genre createDefault() {
        return new Genre();
    }

    @Override
    protected Genre mapRow(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("id"));
        genre.setName(resultSet.getString("name"));
        return genre;
    }

    @Override
    protected SqlRecord mapObject(Genre object) {
        SqlRecord record = new SqlRecord();
        record.put("id", object.getId());
        record.put("name", object.getName());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Genre object) {
        SqlRecord record = new SqlRecord();
        record.put("id", object.getId());
        return record;
    }

    public Genre findByName(String name) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE name = ?";
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{name});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }

    public Genre findById(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{id});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }
}
