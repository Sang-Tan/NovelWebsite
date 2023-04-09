package repository.intermediate;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.intermediate.ChapterMark;

import java.sql.SQLException;
import java.util.List;

public class ChapterMarkRepository extends BaseRepository<ChapterMark> {
    private static ChapterMarkRepository instance;

    public static ChapterMarkRepository getInstance() {
        if (instance == null) {
            instance = new ChapterMarkRepository();
        }
        return instance;
    }

    @Override
    protected ChapterMark createEmpty() {
        return new ChapterMark();
    }

    public List<ChapterMark> getChapterMarksByUserId(Integer userId) throws SQLException {
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }

        String sql = "SELECT * FROM chapter_mark WHERE user_id = ?";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(userId));
        return mapObjects(records);
    }

}
