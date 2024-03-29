package controller;

import core.logging.BasicLogger;
import org.json.JSONException;
import org.json.JSONObject;
import service.ViewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "ViewInNovel", value = "/view-novel/*")
public class ViewInNovelController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            if (request.getParameter("action").equals("viewed")) {
                Integer chapterId = Integer.parseInt(request.getParameter("chapterId"));
                String sessionId = request.getSession().getId();
                synchronized (this) {
                    viewChapter(chapterId, sessionId, request, response);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    public void viewChapter(Integer chapterId, String sessionId, HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        try {
            ViewService.getInstance().addViewToCache(chapterId, sessionId, 1);
            response.getWriter().write(getSuccessJsonString());
        } catch (Exception e) {
            BasicLogger.getInstance().printStackTrace(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getErrorJsonString(String error) throws JSONException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", error);
        return jsonObject.toString();
    }

    private String getSuccessJsonString() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "success");
        return jsonObject.toString();
    }


}
