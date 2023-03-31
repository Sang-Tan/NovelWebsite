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

    protected VolumeRepository() {
        super("volumes", new String[]{"id"});
    }

    @Override
    protected Volume createDefault() {
        Volume volume = new Volume();
        volume.setImage(Volume.DEFAULT_IMAGE);
        return volume;
    }

    @Override
    protected Volume mapRow(ResultSet resultSet) throws SQLException {
        Volume volume = new Volume();
        volume.setId(resultSet.getInt("id"));
        volume.setNovelId(resultSet.getInt("novel_id"));
        volume.setName(resultSet.getString("name"));
        volume.setOrderIndex(resultSet.getInt("order_index"));
        volume.setImage(resultSet.getString("image"));
        return volume;
    }

    @Override
    protected SqlRecord mapObject(Volume volume) {
        SqlRecord record = new SqlRecord();
        record.put("id", volume.getId());
        record.put("volume_id", volume.getNovelId());
        record.put("name", volume.getName());
        record.put("image", volume.getImage());
        record.put("order_index", volume.getOrderIndex());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Volume volume) {
        SqlRecord record = new SqlRecord();
        record.put("id", volume.getId());
        return record;
    }

    public Volume getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ID});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }
}
