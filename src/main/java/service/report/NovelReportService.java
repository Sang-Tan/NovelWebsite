package service.report;

import core.pagination.Paginator;
import model.NovelReport;
import repository.NovelReportRepository;
import service.PagingService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovelReportService {
    private static NovelReportService instance;
    private String sql;
    private Paginator paginator;

    private NovelReportService(){

    }
    public static NovelReportService getInstance() {
        if (instance == null) {
            instance = new NovelReportService();
        }
        return instance;
    }

    public String getSql() {
        return sql;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public List<NovelReport> getAllNovelReport(int page) throws SQLException {
        List<Object> params = new ArrayList<>();
        List<String> conditionsSQL = new ArrayList<>();
        sql = conditionsSQL.size() > 0 ? String.join(" AND ", conditionsSQL) : "1=1";
        paginator = new Paginator(NovelReportRepository.getInstance().countNovelReports(sql, params), page, 4);
        sql += " " + PagingService.generatePaginationCondition(params, paginator);
        return NovelReportRepository.getInstance().getAllNovelReport(sql, params);
    }
}
