package service.logging;

import model.Volume;
import model.logging.VolumeApprovalLog;
import repository.logging.VolumeApprovalLogRepository;

import java.sql.SQLException;
import java.util.List;

public class VolumeApprovalLoggingService extends ApprovalLoggingService<Volume, VolumeApprovalLog> {

    private static VolumeApprovalLoggingService instance;

    public static VolumeApprovalLoggingService getInstance() {
        if (instance == null) {
            synchronized (VolumeApprovalLoggingService.class) {
                if (instance == null) {
                    instance = new VolumeApprovalLoggingService();
                }
            }
        }
        return instance;
    }

    private VolumeApprovalLoggingService() {
        super();
    }

    @Override
    public List<VolumeApprovalLog> getLogsByResourceId(Integer volumeId) throws SQLException {
        return VolumeApprovalLogRepository.getInstance().getOrderedLogsByVolumeId(volumeId, true);
    }

    @Override
    public VolumeApprovalLog saveLog(VolumeApprovalLog log) throws SQLException {
        return VolumeApprovalLogRepository.getInstance().insert(log);
    }
}
