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
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "reject":
                reject(req, resp);
                break;

            case "approve":
                approve(req, resp);
                break;

            default:
                showList(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void reject(HttpServletRequest req, HttpServletResponse resp) {
        Novel novel = new Novel();
        Chapter chapter = new Chapter();

        int idNovel = 0;
        if (req.getParameter("idNovel") != null) {
            idNovel = Integer.parseInt(req.getParameter("idNovel"));
        }

        int idChapter = 0;
        if (req.getParameter("idChapter") != null) {
            idChapter = Integer.parseInt(req.getParameter("idChapter"));
        }

        try {
            novel = NovelRepository.getInstance().getById(idNovel);
            chapter = ChapterRepository.getInstance().getById(idChapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idChapter == 0) {
            novel.setApprovalStatus(Novel.APPROVE_STATUS_REJECTED);
            try {
                NovelRepository.getInstance().update(novel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            chapter.setApprovalStatus(Chapter.APPROVE_STATUS_REJECTED);
            try {
                ChapterRepository.getInstance().update(chapter);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        showList(req, resp);
    }

    private void approve(HttpServletRequest req, HttpServletResponse resp) {
        Novel novel = new Novel();
        Chapter chapter = new Chapter();

        int idNovel = 0;
        if (req.getParameter("idChapter") != null) {
            idNovel = Integer.parseInt(req.getParameter("idNovel"));
        }

        int idChapter = 0;
        if (req.getParameter("idChapter") != null) {
            idChapter = Integer.parseInt(req.getParameter("idChapter"));
        }
        if (req.getParameter("idChapter") != null) {
            idChapter = Integer.parseInt(req.getParameter("idChapter"));
        }
        try {
            novel = NovelRepository.getInstance().getById(idNovel);
            chapter = ChapterRepository.getInstance().getById(idChapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idChapter == 0) {
            novel.setApprovalStatus(Novel.APPROVE_STATUS_APPROVED);
            try {
                NovelRepository.getInstance().update(novel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            chapter.setApprovalStatus(Chapter.APPROVE_STATUS_APPROVED);
            try {
                ChapterRepository.getInstance().update(chapter);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        showList(req, resp);
    }

    public void showList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("novelList", NovelRepository.getInstance().getAllPendingNovel(Novel.APPROVE_STATUS_PENDING));
            req.setAttribute("chapterList", ChapterRepository.getInstance().getAllPendingChapter(Chapter.APPROVE_STATUS_PENDING));
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
