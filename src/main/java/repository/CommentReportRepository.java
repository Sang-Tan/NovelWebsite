package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.CommentReport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentReportRepository extends BaseRepository<CommentReport> {
    private static CommentReportRepository instance;

    public static CommentReportRepository getInstance() {
        if (instance == null) {
            instance = new CommentReportRepository();
        }
        return instance;
    }

    @Override
    protected CommentReport createEmpty() {
        return new CommentReport();
    }



}
