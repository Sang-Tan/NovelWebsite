package service.upload_change;

import model.Volume;
import model.temporary.VolumeChange;
import repository.VolumeRepository;
import repository.temporary.VolumeChangeRepository;

import java.sql.SQLException;

public class VolumeChangeService extends BaseChangeService<VolumeChange> {

    private static VolumeChangeService instance;

    public static VolumeChangeService getInstance() {
        if (instance == null) {
            synchronized (VolumeChangeService.class) {
                if (instance == null) {
                    instance = new VolumeChangeService(Volume.APPROVE_STATUS_APPROVED,
                            Volume.APPROVE_STATUS_REJECTED, Volume.APPROVE_STATUS_PENDING);
                }
            }
        }
        return instance;
    }

    protected VolumeChangeService(String statusApproved, String statusRejected, String statusPending) {
        super(statusApproved, statusRejected, statusPending);
    }

    @Override
    protected String getApprovalStatus(int id) throws SQLException {
        Volume volume = VolumeRepository.getInstance().getById(id);
        return volume.getApprovalStatus();
    }

    @Override
    public VolumeChange getChangeByResourceId(int id) throws SQLException {
        return VolumeChangeRepository.getInstance().getByVolumeId(id);
    }
}
