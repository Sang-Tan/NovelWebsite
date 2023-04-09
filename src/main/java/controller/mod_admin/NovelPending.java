package controller.mod_admin;

import model.Chapter;
import model.Novel;
import repository.ChapterRepository;
import repository.NovelRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "NovelPending", value = "/mod/duyet-truyen")
public class NovelPending extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showList(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action){
            case "reject":

                break;

            case "approve":

                break;

            default:
                showList(req, resp);
        }

    }

    public void showList(HttpServletRequest req, HttpServletResponse resp){
        try {
            req.setAttribute("novelList", NovelRepository.getInstance().getAllPendingNovel(Novel.STATUS_PENDING));
            req.setAttribute("chapterList", ChapterRepository.getInstance().getAllPendingChapter(Chapter.STATUS_PENDING));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/mod_admin/novel_pending.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
