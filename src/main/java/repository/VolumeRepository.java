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
    protected Volume createDefault() {
        Volume volume = new Volume();
        volume.setImage(Volume.DEFAULT_IMAGE);
        return volume;
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
