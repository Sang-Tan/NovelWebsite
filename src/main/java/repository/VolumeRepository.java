package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import model.Volume;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public Volume createDefault() {
        Volume volume = new Volume();
        volume.setImage(Volume.DEFAULT_IMAGE);
        return volume;
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
        String sql = String.format("SELECT MAX(order_index) FROM %s WHERE novel_id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{novelId});
        if (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }

    public Volume getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ID});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }

    public List<Volume> getByNovelId(int novelId) throws SQLException {
        //don't get volume with order_index = 0 because it's not a real volume
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ? " +
                "AND order_index > 1 " +
                "ORDER BY order_index ASC", getTableName());
        ArrayList<Volume> volumes = new ArrayList<>();
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{novelId});
        while (result.next()) {
            volumes.add(mapObject(result));
        }
        return volumes;
    }

    public Volume getVirtualVolumeByNovelId(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE novel_id = ? " +
                "AND order_index = 1", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{novelId});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }

    public Volume getByChapterId(int chapterId) throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "WHERE id = (SELECT volume_id FROM chapters WHERE id = ?)", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{chapterId});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }

}
