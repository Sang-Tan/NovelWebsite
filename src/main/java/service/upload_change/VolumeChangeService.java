package service.upload_change;

import model.Volume;
import model.temporary.VolumeChange;
import repository.VolumeRepository;
import repository.temporary.VolumeChangeRepository;
import service.upload.FileMapper;
import service.upload_change.base.BaseChangeService;

import java.io.IOException;
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

        if (!oldVolumeInfo.getName().equals(newVolumeInfo.getName())) {
            volumeChange.setName(newVolumeInfo.getName());
        }

        if (!oldVolumeInfo.getImage().equals(newVolumeInfo.getImage())) {
            volumeChange.setImage(newVolumeInfo.getImage());
        }

        if (volumeChange.getName() != null || volumeChange.getImage() != null) {
            VolumeChangeRepository.getInstance().insert(volumeChange);
        }

    }

    @Override
    protected void mergeChange(int volumeId) throws SQLException, IOException {
        Volume volume = VolumeRepository.getInstance().getById(volumeId);
        VolumeChange volumeChange = VolumeChangeRepository.getInstance().getByVolumeId(volumeId);

        if (volumeChange.getName() != null) {
            volume.setName(volumeChange.getName());
        }

        if (volumeChange.getImage() != null) {
            FileMapper oldImage = FileMapper.mapURI(volume.getImage());
            FileMapper newImage = FileMapper.mapURI(volumeChange.getImage());
            oldImage.copyFrom(newImage);
        }

        VolumeRepository.getInstance().update(volume);
        deleteVolumeChange(volumeChange);
    }

    @Override
    protected void approveNewResource(int volumeId) throws SQLException {
        Volume volume = VolumeRepository.getInstance().getById(volumeId);
        volume.setApprovalStatus(Volume.APPROVE_STATUS_APPROVED);
        VolumeRepository.getInstance().update(volume);
    }

    @Override
    protected void rejectAndDeleteChange(int volumeId) throws SQLException {
        deleteVolumeChange(volumeId);
    }

    @Override
    protected void rejectNewResource(int id) throws SQLException {
        Volume volume = VolumeRepository.getInstance().getById(id);
        volume.setApprovalStatus(Volume.APPROVE_STATUS_REJECTED);
        VolumeRepository.getInstance().update(volume);
    }

    protected void deleteVolumeChange(int id) throws SQLException {
        VolumeChange volumeChange = VolumeChangeRepository.getInstance().getByVolumeId(id);
        deleteVolumeChange(volumeChange);
    }

    protected void deleteVolumeChange(VolumeChange volumeChange) throws SQLException {
        String imageURI = volumeChange.getImage();
        if (imageURI != null) {
            FileMapper image = FileMapper.mapURI(imageURI);
            image.delete();
        }

        VolumeChangeRepository.getInstance().delete(volumeChange);
    }
}
