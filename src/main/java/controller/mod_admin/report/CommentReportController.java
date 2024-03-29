package controller.mod_admin.report;

import core.logging.BasicLogger;
import core.metadata.ReportSelection;
import core.pagination.Paginator;
import model.CommentReport;
import org.json.JSONArray;
import repository.CommentReportRepository;
import service.PagingService;
import service.report.CommentReportService;

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

@WebServlet(urlPatterns = "/mod/bao-cao-binh-luan")
public class CommentReportController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CommentReportController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String type = req.getParameter("type");
            if (type == null) {
                showList(req, resp);
            } else if (type.equals("comment_report")) {
                getReportsInComment(req, resp);
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
            case "checked":
                try {
                    setCheckTime(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                try {
                    deleteCommentReport(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void deleteCommentReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException{
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        CommentReportRepository.getInstance().deleteReport(commentId);
    }

    private void setCheckTime(HttpServletRequest req, HttpServletResponse resp) throws SQLException{
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        CommentReportRepository.getInstance().setCheckTime(commentId);
    }
    private void showList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = Integer.parseInt(req.getParameter("page") == null ? "1" : req.getParameter("page"));
        Paginator paginator;
        try {
            req.setAttribute("commentReportList", CommentReportService.getInstance().getAllCommentReport(page));
            paginator = CommentReportService.getInstance().getPaginator();
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        req.setAttribute("selection", ReportSelection.COMMENT_REPORT);
        req.setAttribute("pageItems", PagingService.getActivePageItems(paginator));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/mod_admin/report/main_report_page.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            resp.setStatus(500);
            CommentReportController.LOGGER.warning(e.getMessage());
        }
    }

    private void getReportsInComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        if (req.getParameter("comment-id") == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Integer commentId = Integer.parseInt(req.getParameter("comment-id"));
        List<CommentReport> reports = CommentReportRepository.getInstance().getAllReportContentByCommentId(commentId);
        JSONArray jsonArr = new JSONArray();
        for (CommentReport commentReport : reports) {
            jsonArr.put(commentReport.getReason());
        }

        resp.setHeader("Content-Type", "application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(jsonArr);

    }
}