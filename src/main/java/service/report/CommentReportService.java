package service.report;

import core.pagination.Paginator;
import model.Comment;
import model.CommentReport;
import repository.CommentReportRepository;
import repository.CommentRepository;
import service.PagingService;

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

    public List<Comment> getAllCommentReport(int page) throws SQLException {
        List<Object> params = new ArrayList<>();
        List<String> conditionsSQL = new ArrayList<>();
        sql = conditionsSQL.size() > 0 ? String.join(" AND ", conditionsSQL) : "1=1";
        paginator = new Paginator(CommentReportRepository.getInstance().countCommentReports(sql, params), page, 4);
        sql += " " + PagingService.generatePaginationCondition(params, paginator);
        return CommentRepository.getInstance().getAllCommentReport(sql, params);
    }

    public boolean isReportExist(int commentId, int reporterId) throws SQLException {
        return CommentReportRepository.getInstance().isReportExist(commentId, reporterId);
    }
}