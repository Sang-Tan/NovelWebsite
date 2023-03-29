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

    protected ChapterRepository() {
        super("chapters", new String[]{"id"});
    }

    @Override
    protected Chapter createDefault() {
        Chapter chapter = new Chapter();
        chapter.setContent(Chapter.DEFAULT_CONTENT);
        chapter.setPending(true);
        return chapter;
    }

    @Override
    protected Chapter mapRow(ResultSet resultSet) throws SQLException {
        Chapter chapter = new Chapter();
        chapter.setId(resultSet.getInt("id"));
        chapter.setName(resultSet.getString("name"));
        chapter.setOrderIndex(resultSet.getInt("order_index"));
        chapter.setVolumeId(resultSet.getInt("volume_id"));
        chapter.setModifyTime(resultSet.getTimestamp("modify_time"));
        chapter.setPending(resultSet.getBoolean("is_pending"));
        return chapter;
    }

    @Override
    protected SqlRecord mapObject(Chapter chapter) {
        SqlRecord record = new SqlRecord();
        record.put("id", chapter.getId());
        record.put("name", chapter.getName());
        record.put("order_index", chapter.getOrderIndex());
        record.put("volume_id", chapter.getVolumeId());
        record.put("modify_time", chapter.getModifyTime());
        record.put("is_pending", chapter.isPending());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Chapter chapter) {
        SqlRecord record = new SqlRecord();
        record.put("id", chapter.getId());
        return record;
    }

    public Chapter getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().query(sql, new Object[]{ID});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }
}
