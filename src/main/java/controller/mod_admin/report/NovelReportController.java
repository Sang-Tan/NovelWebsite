package controller.mod_admin.report;

import core.logging.BasicLogger;
import core.metadata.ReportSelection;
import core.pagination.Paginator;
import model.NovelReport;
import org.json.JSONArray;
import repository.NovelReportRepository;
import service.PagingService;
import service.report.CommentReportService;
import service.report.NovelReportService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/mod/bao-cao-truyen")
public class NovelReportController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(NovelReportController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String type = req.getParameter("type");
            if (type == null) {
                showList(req, resp);
            } else if (type.equals("novel_report")) {
                getReportsInNovel(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            BasicLogger.getInstance().printStackTrace(e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "report_novel":
                try {
                    postNovelReport(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

        }
    }

    private void postNovelReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        int novelId = Integer.parseInt(req.getParameter("novelId"));
        int reportId = Integer.parseInt(req.getParameter("userId"));
        String reason = req.getParameter("reason");
        NovelReport novelReport = new NovelReport();
        novelReport.setNovelId(novelId);
        novelReport.setReporterId(reportId);
        novelReport.setReason(reason);

        NovelReportRepository.getInstance().insert(novelReport);
    }

    private void getReportsInNovel(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        if (req.getParameter("novel-id") == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Integer novelId = Integer.parseInt(req.getParameter("novel-id"));
        List<NovelReport> reports = NovelReportRepository.getInstance().getAllReportContentByNovelId(novelId);
        JSONArray jsonArr = new JSONArray();
        for (NovelReport novelReport : reports) {
            jsonArr.put(novelReport.getReason());
        }

        resp.setHeader("Content-Type", "application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(jsonArr);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = Integer.parseInt(req.getParameter("page") == null ? "1" : req.getParameter("page"));
        Paginator paginator;
        try {
            req.setAttribute("novelReportList", NovelReportService.getInstance().getAllNovelReport(page));
            paginator = NovelReportService.getInstance().getPaginator();
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        req.setAttribute("selection", ReportSelection.NOVEL_REPORT);
        req.setAttribute("pageItems", PagingService.getActivePageItems(paginator));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/mod_admin/report/main_report_page.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            resp.setStatus(500);
            NovelReportController.LOGGER.warning(e.getMessage());
        }
    }
}
