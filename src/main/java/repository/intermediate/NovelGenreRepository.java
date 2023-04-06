package repository.intermediate;

import core.database.BaseRepository;
import core.database.MySQLdb;
import model.intermediate.NovelGenre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NovelGenreRepository extends BaseRepository<NovelGenre> {

    private static NovelGenreRepository instance;

    public static NovelGenreRepository getInstance() {
        if (instance == null) {
            instance = new NovelGenreRepository();
        }
        return instance;
    }

    @Override
    protected NovelGenre createEmpty() {
        return new NovelGenre();
    }

    public List<NovelGenre> getByNovelId(int novelId) throws Exception {
        String sql = "SELECT * FROM " + getTableName() + " WHERE novel_id = ?";
        return mapObjects(MySQLdb.getInstance().select(sql, new Object[]{novelId}));
    }

    @Override
    public NovelGenre createDefault() {
        return new NovelGenre();
    }

    public List<NovelGenre> getByNovelId(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{novelId});
        return mapObjects(result);
    }

    public void deleteByNovelId(int novelId) throws SQLException {
        List<NovelGenre> novelGenres = getByNovelId(novelId);
        for (NovelGenre novelGenre : novelGenres) {
            delete(novelGenre);
        }
    }
}
