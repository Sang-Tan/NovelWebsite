package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import core.logging.BasicLogger;
import model.Volume;

import java.sql.SQLException;
import java.util.List;

public class VolumeRepository extends BaseRepository<Volume> {
    private static VolumeRepository instance;

    public static VolumeRepository getInstance() {
        if (instance == null) {
            instance = new VolumeRepository();
        }
        return instance;
    }

    @Override
    protected Volume createEmpty() {
        return new Volume();
    }

    @Override
    public Volume insert(Volume object) throws SQLException {
        //check valid
        int novelId = object.getNovelId();
        if (NovelRepository.getInstance().getById(novelId) == null) {
            throw new SQLException(String.format("Novel id %d not found", novelId));
        }

        int maxIndex = getMaxVolumeIndexInNovel(novelId);
        object.setOrderIndex(maxIndex + 1);

        return super.insert(object);
    }

    @Override
    public void delete(Volume object) throws SQLException {
        super.delete(object);
    }

    /**
     * Get max order index in a novel
     *
     * @param novelId id of novel
     * @return max order index, 0 if no volume in novel
     * @throws SQLException
     */
    public int getMaxVolumeIndexInNovel(int novelId) throws SQLException {
        String sql = String.format("SELECT MAX(order_index) as max_index FROM %s WHERE novel_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        SqlRecord record = records.get(0);
        Object maxIndex = record.get("max_index");
        if (maxIndex == null) {
            return 0;
        }
        return (int) maxIndex;
    }

    public Volume getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public List<Volume> getByNovelId(int novelId) throws SQLException {
        //don't get volume with order_index = 0 because it's not a real volume
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ? " +
                "AND order_index > 1 " +
                "ORDER BY order_index ASC", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        return mapObjects(records);
    }

    public Volume getVirtualVolumeByNovelId(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ? " +
                "AND order_index = 1", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        if (records.size() > 1) {
            BasicLogger.getInstance().getLogger().warning("More than 1 virtual volume in novel " + novelId);
        }
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public Volume getByChapterId(int chapterId) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE id = (SELECT volume_id FROM chapters WHERE id = ?)", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(chapterId));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

}
