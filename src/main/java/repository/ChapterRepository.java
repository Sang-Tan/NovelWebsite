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
    public Chapter createDefault() {
        Chapter chapter = new Chapter();
        chapter.setPending(true);
        return chapter;
    }

    @Override
    public Chapter insert(Chapter object) throws SQLException {
        //check valid
        int volumeId = object.getVolumeId();
        if (VolumeRepository.getInstance().getById(volumeId) == null) {
            throw new SQLException(String.format("Volume id %d not found", volumeId));
        }

        int maxIndex = getMaxChapterIndexInVolume(volumeId);
        object.setOrderIndex(maxIndex + 1);

        return super.insert(object);
    }

    public int getMaxChapterIndexInVolume(int volumeId) throws SQLException {
        String sql = String.format("SELECT MAX(order_index) AS max_index FROM %s WHERE volume_id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{volumeId});
        if (result.next()) {
            return result.getInt("max_index");
        }
        return 0;
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
