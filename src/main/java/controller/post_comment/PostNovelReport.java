package controller.post_comment;

import core.logging.BasicLogger;
import model.NovelReport;
import org.json.JSONException;
import org.json.JSONObject;
import repository.NovelReportRepository;
import service.report.NovelReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/bao-cao-truyen")
public class PostNovelReport extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        if (action.equals("report_novel")) {
            try {
                resp.setHeader("Content-Type", "application/json; charset=UTF-8");

                postNovelReport(req, resp);
            } catch (Exception e) {
                BasicLogger.getInstance().printStackTrace(e);
                resp.setStatus(500);
                return;
            }
        }
    }

    private void postNovelReport(HttpServletRequest req, HttpServletResponse resp) throws SQLException, JSONException, IOException {
        int novelId = Integer.parseInt(req.getParameter("novelId"));
        int reportId = Integer.parseInt(req.getParameter("userId"));

        if (NovelReportService.isReported(novelId, reportId)) {
            JSONObject errorJSON = getResponseJSON("error", "Bạn đã báo cáo truyện này rồi");
            Writer writer = resp.getWriter();
            writer.write(errorJSON.toString());
            return;
        }

        String reason = req.getParameter("reason");

        if (reason == null || reason.isEmpty()) {
            JSONObject errorJSON = getResponseJSON("error", "Phải ghi rõ lý do báo cáo");
            Writer writer = resp.getWriter();
            writer.write(errorJSON.toString());
            return;
        }

        NovelReport novelReport = new NovelReport();
        novelReport.setNovelId(novelId);
        novelReport.setReporterId(reportId);
        novelReport.setReason(reason);

        NovelReportRepository.getInstance().insert(novelReport);

        JSONObject successJSON = getResponseJSON("success", null);
        Writer writer = resp.getWriter();
        writer.write(successJSON.toString());
    }

    private JSONObject getResponseJSON(String status, String message) throws JSONException {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        if (message != null) {
            responseJSON.put("message", message);
        }

        return responseJSON;
    }
}
