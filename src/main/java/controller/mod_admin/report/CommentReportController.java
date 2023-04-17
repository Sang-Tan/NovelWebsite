package controller.mod_admin.report;

import core.logging.BasicLogger;
import core.metadata.ReportSelection;
import model.CommentReport;
import org.json.JSONArray;
import repository.CommentReportRepository;

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

@WebServlet(urlPatterns = "/mod/bao-cao-binh-luan")
public class CommentReportController extends HttpServlet {
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

    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("commentReportList", CommentReportRepository.getInstance().getAllCommentReport());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("selection", ReportSelection.COMMENT_REPORT);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/mod_admin/report/main_report_page.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
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
        for (CommentReport commentReport : reports)
        {
            jsonArr.put(commentReport.getReason());
        }

        resp.setHeader("Content-Type", "application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(jsonArr);

    }
}
