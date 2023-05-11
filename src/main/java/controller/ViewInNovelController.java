package controller;

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
            if(request.getParameter("action").equals("viewed")) {
                Integer chapterId = Integer.parseInt(request.getParameter("chapterId"));
                String ipAddress = request.getParameter("ipAddress");
                synchronized (this) {
                    viewChapter(chapterId, ipAddress, request, response);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    public void viewChapter(Integer chapterId, String ipAddress,HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        try {
            ViewService.getInstance().addViewToCache(chapterId,ipAddress, 1);
            response.getWriter().write(getSuccessJsonString());
        }
        catch (Exception e) {
            response.getWriter().write(getErrorJsonString(e.getMessage()));
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
