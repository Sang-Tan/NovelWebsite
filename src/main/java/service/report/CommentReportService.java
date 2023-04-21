package service.report;

import model.CommentReport;
import repository.CommentReportRepository;
import service.Pagination.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentReportService {
    private static CommentReportService instance;
    private String sql;
    private Paginator paginator;

    private CommentReportService() {
    }

    public static CommentReportService getInstance() {
        if (instance == null) {
            instance = new CommentReportService();
        }
        return instance;
    }

    public String getSql() {
        return sql;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public List<CommentReport> getAllCommentReport(int page) throws SQLException {
        List<Object> params = new ArrayList<>();
        List<String> conditionsSQL = new ArrayList<>();
        sql = conditionsSQL.size() > 0 ? String.join(" AND ", conditionsSQL) : "1=1";
        paginator = new Paginator(CommentReportRepository.getInstance().countCommentReports(sql, params), page);
        sql += " " + paginator.generatePaginationCondition(params);
        return CommentReportRepository.getInstance().getAllCommentReport(sql, params);
    }


}
