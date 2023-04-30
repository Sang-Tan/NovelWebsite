package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import core.logging.BasicLogger;
import model.Chapter;
import model.Volume;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

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
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(volumeId));
        Object maxIndex = records.get(0).get("max_index");
        if (maxIndex == null) {
            return 0;
        }
        return (int) maxIndex;
    }

    public Chapter getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public List<Chapter> getByVolumeId(int volumeId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE volume_id = ? " +
                "ORDER BY order_index ASC", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(volumeId));
        return mapObjects(records);
    }

    public Chapter getVirtualChapter(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE volume_id IN " +
                "(SELECT id FROM volumes " +
                "WHERE order_index = 1 " +
                "AND novel_id = ?) ", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        if (records.size() > 1) {
            BasicLogger.getInstance().getLogger().
                    warning(String.format("Multiple virtual chapter found for novel id %d", novelId));
        }
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        BasicLogger.getInstance().getLogger().
                warning(String.format("Virtual chapter not found for novel id %d", novelId));
        return null;
    }

    public Chapter getPreviousChapter(int chapterID) throws SQLException {
        Chapter chapter = getById(chapterID);
        if (chapter == null) {
            return null;
        }
        int volumeId = chapter.getVolumeId();
        int orderIndex = chapter.getOrderIndex();
        String sql = String.format("SELECT * FROM %s " +
                "WHERE volume_id = ? " +
                "AND order_index < ? " +
                "ORDER BY order_index DESC " +
                "LIMIT 1", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(volumeId, orderIndex));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        // in case previous chapter is in previous volume
        Volume previousVolume = VolumeRepository.getInstance().getPreviousVolume(volumeId);
        if (previousVolume == null) return null;
        return previousVolume.getChapters().get(previousVolume.getChapters().size() - 1);
    }

    public Chapter getNextChapter(int chapterID) throws SQLException {
        Chapter chapter = getById(chapterID);
        if (chapter == null) {
            return null;
        }
        int volumeId = chapter.getVolumeId();
        int orderIndex = chapter.getOrderIndex();
        String sql = String.format("SELECT * FROM %s " +
                "WHERE volume_id = ? " +
                "AND order_index > ? " +
                "ORDER BY order_index ASC " +
                "LIMIT 1", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(volumeId, orderIndex));
        for (SqlRecord record : records) {
            return mapObject(record);
        }

        // in case next chapter is in next volume
        Volume nextVolume = VolumeRepository.getInstance().getNextVolumeId(volumeId);
        if (nextVolume == null) return null;
        return nextVolume.getChapters().get(0);
    }

    public Chapter getLastChapterOfNovel(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE volume_id IN " +
                "(SELECT id FROM volumes " +
                "WHERE novel_id = ? " +
                "AND NOT order_index = 1) " +
                "ORDER BY create_at DESC " +
                "LIMIT 1", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public Collection<Chapter> getAllPendingChapter(String approvalStatus) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE approval_status = ?", getTableName());
        sql += "ORDER BY modify_time DESC";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(approvalStatus));
        return mapObjects(records);
    }

    public List<Chapter> getInModeratingChapterQueue() throws SQLException {
        String sql = "SELECT * FROM chapters " +
                "WHERE (approval_status = 'pending') " +
                "OR (approval_status = 'approved' AND " +
                "EXISTS( SELECT chapter_id FROM chapter_changes " +
                "WHERE chapter_id = chapters.id)) " +
                "ORDER BY updated_at ASC";
        List<SqlRecord> records = MySQLdb.getInstance().select(sql);
        return mapObjects(records);
    }
}
