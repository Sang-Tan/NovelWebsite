package controller.post_comment;

import model.CommentReport;
import repository.CommentReportRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/bao-cao-binh-luan")
public class PostCommentReport extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        if (action.equals("report_comment")) {
            try {
                postCommentReport(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void postCommentReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        int reportId = Integer.parseInt(req.getParameter("userId"));
        String reason = req.getParameter("reason");
        CommentReport commentReport = new CommentReport();
        commentReport.setCommentId(commentId);
        commentReport.setReporterId(reportId);
        commentReport.setReason(reason);

        CommentReportRepository.getInstance().insert(commentReport);
    }
}
