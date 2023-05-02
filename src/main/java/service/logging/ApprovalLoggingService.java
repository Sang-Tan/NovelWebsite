package service.logging;

import model.INovelContent;
import model.logging.IApprovalLog;

import java.util.List;

public abstract class ApprovalLoggingService<R extends INovelContent, T extends IApprovalLog> {
    public abstract List<T> getLogsByResourceId(Integer resourceId);

    public abstract List<T> saveLogs(List<T> logs);
}
