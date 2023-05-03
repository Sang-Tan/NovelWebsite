package controller.post_comment;

import model.NovelReport;
import repository.NovelReportRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/bao-cao-truyen")
public class PostNovelReport extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        if (action.equals("report_novel")) {
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
}
