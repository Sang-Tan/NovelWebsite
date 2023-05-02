package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.NovelReport;

import java.sql.SQLException;
import java.util.List;

public class NovelReportRepository extends BaseRepository<NovelReport> {
    private static NovelReportRepository instance;

    public static NovelReportRepository getInstance() {
        if (instance == null) {
            instance = new NovelReportRepository();
        }
        return instance;
    }

    @Override
    protected NovelReport createEmpty() {
        return new NovelReport();
    }

    public List<NovelReport> getAllNovelReport(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT * FROM %s GROUP BY novel_id ORDER BY max(report_time) DESC LIMIT ? OFFSET ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return mapObjects(records);
    }

    public List<NovelReport> getAllReportContentByNovelId(int novelId) throws SQLException {
        String sql = String.format("SELECT * FROM %s " + "WHERE novel_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        return mapObjects(records);
    }

    public long countNovelReports(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT COUNT(id) FROM %s WHERE %s", getTableName(), condition);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        for (SqlRecord record : records) {
            return (long) record.get("COUNT(id)");
        }
        return 0;
    }

    public void setCheckTime(int novelId) throws SQLException {
        String sql = String.format("UPDATE %s SET check_time = CURRENT_TIMESTAMP WHERE novel_id = ?", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(novelId));

    }
}
