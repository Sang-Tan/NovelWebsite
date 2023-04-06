package repository.intermediate;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.intermediate.NovelGenre;

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

    public List<NovelGenre> getByNovelId(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        return mapObjects(records);
    }

    public void deleteByNovelId(int novelId) throws SQLException {
        List<NovelGenre> novelGenres = getByNovelId(novelId);
        for (NovelGenre novelGenre : novelGenres) {
            delete(novelGenre);
        }
    }
}
