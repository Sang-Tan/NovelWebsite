package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Chapter;
import model.Volume;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChapterRepository extends BaseRepository<Chapter> {
    private static ChapterRepository instance;

    public static ChapterRepository getInstance() {
        if (instance == null) {
            instance = new ChapterRepository();
        }
        return instance;
    }

    @Override
    protected Chapter createEmpty() {
        return new Chapter();
    }

    @Override
    protected Chapter createDefault() {
        Chapter chapter = new Chapter();
        chapter.setContent(Chapter.DEFAULT_CONTENT);
        chapter.setIsPending(true);
        return chapter;
    }

    public Chapter getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ID});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }
}
