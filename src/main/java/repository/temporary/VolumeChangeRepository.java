package repository.temporary;

import core.database.BaseRepository;
import model.temporary.VolumeChange;

import java.sql.SQLException;

public class VolumeChangeRepository extends BaseRepository<VolumeChange> {
    private static VolumeChangeRepository instance;

    public static VolumeChangeRepository getInstance() {
        if (instance == null) {
            synchronized (VolumeChangeRepository.class) {
                if (instance == null) {
                    instance = new VolumeChangeRepository();
                }
            }
        }
        return instance;
    }

    @Override
    protected VolumeChange createEmpty() {
        return new VolumeChange();
    }

    public VolumeChange getByVolumeId(int volumeId) throws SQLException {
        VolumeChange volumeChange = new VolumeChange();
        volumeChange.setVolumeId(volumeId);
        return getByPrimaryKey(volumeChange);
    }

    public void deleteByVolumeId(int volumeId) throws SQLException {
        VolumeChange volumeChange = new VolumeChange();
        volumeChange.setVolumeId(volumeId);
        delete(volumeChange);
    }
}
