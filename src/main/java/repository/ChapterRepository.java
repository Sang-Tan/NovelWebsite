package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.Chapter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChapterRepository extends BaseRepository<Chapter> {
    protected ChapterRepository() {
        super("chapters", new String[]{"id"});
    }

    @Override
    protected Chapter createDefault() {
        Chapter chapter = new Chapter();
        chapter.setContent("Không có nội dung");
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
        chapter.setPending(resultSet.getBoolean("pending"));
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
        record.put("pending", chapter.isPending());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Chapter chapter) {
        SqlRecord record = new SqlRecord();
        record.put("id", chapter.getId());
        return record;
    }
}
