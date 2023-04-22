package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.CommentReport;
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

    public List<NovelReport> getAllNovelReport() throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "GROUP BY novel_id ORDER BY report_time DESC", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql);
        return mapObjects(records);
    }

    public List<NovelReport> getAllReportContentByNovelId(int novelId) throws SQLException{
        String sql = String.format("SELECT * FROM %s " + "WHERE novel_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(novelId));
        return mapObjects(records);
    }
}
