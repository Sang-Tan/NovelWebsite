package controller.post_comment;

import core.logging.BasicLogger;
import model.CommentReport;
import org.json.JSONException;
import org.json.JSONObject;
import repository.CommentReportRepository;
import service.report.CommentReportService;

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
                resp.setHeader("Content-Type", "application/json; charset=UTF-8");
                postCommentReport(req, resp);
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                BasicLogger.getInstance().printStackTrace(e);
            }
        }
    }

    private void postCommentReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException, JSONException, IOException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        int reportId = Integer.parseInt(req.getParameter("userId"));

        if (CommentReportService.getInstance().isReportExist(commentId, reportId)) {
            JSONObject errorObject = getJsonResponse("error", "Bạn đã báo cáo bình luận này rồi");
            resp.getWriter().println(errorObject);
            return;
        }

        String reason = req.getParameter("reason");

        if (reason == null || reason.equals("")) {
            JSONObject errorObject = getJsonResponse("error", "Vui lòng nhập lý do báo cáo");
            resp.getWriter().println(errorObject);
            return;
        }

        CommentReport commentReport = new CommentReport();
        commentReport.setCommentId(commentId);
        commentReport.setReporterId(reportId);
        commentReport.setReason(reason);

        CommentReportRepository.getInstance().insert(commentReport);

        JSONObject successObject = getJsonResponse("success", null);
        resp.getWriter().println(successObject);
    }

    private JSONObject getJsonResponse(String status, String message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        if (message != null) {
            jsonObject.put("message", message);
        }
        return jsonObject;
    }
}
