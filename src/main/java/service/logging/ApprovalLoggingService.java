package service.logging;

import model.INovelContent;
import model.logging.IApprovalLog;

import java.sql.SQLException;
import java.util.List;

public abstract class ApprovalLoggingService<R extends INovelContent, T extends IApprovalLog> {
    public abstract List<T> getLogsByResourceId(Integer resourceId) throws SQLException;

    public abstract T saveLog(T log) throws SQLException;
}
