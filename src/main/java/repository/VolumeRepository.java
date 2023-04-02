package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Novel;
import model.Volume;

import java.sql.ResultSet;
import java.sql.SQLException;

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


}
