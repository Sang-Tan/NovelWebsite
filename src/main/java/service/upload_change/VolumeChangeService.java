package service.upload_change;

import model.Volume;
import model.temporary.VolumeChange;
import repository.VolumeRepository;
import repository.temporary.VolumeChangeRepository;

import java.sql.SQLException;

public class VolumeChangeService extends BaseChangeService<Volume, VolumeChange> {

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

    @Override
    public void createChange(Volume oldVolumeInfo, Volume newVolumeInfo) throws SQLException {
        VolumeChange volumeChange = new VolumeChange();
        volumeChange.setVolumeId(oldVolumeInfo.getId());

        boolean makeChange = false;

        if (!oldVolumeInfo.getName().equals(newVolumeInfo.getName())) {
            volumeChange.setName(newVolumeInfo.getName());
            makeChange = true;
        }

        if (!oldVolumeInfo.getImage().equals(newVolumeInfo.getImage())) {
            volumeChange.setImage(newVolumeInfo.getImage());
            makeChange = true;
        }

        if (makeChange) {
            VolumeChangeRepository.getInstance().insert(volumeChange);
        }

    }
}
