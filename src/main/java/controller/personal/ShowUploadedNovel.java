package controller.personal;

import core.logging.BasicLogger;
import model.User;
import repository.NovelRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet(urlPatterns = "/ca-nhan/truyen-da-dang")
public class ShowUploadedNovel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");

        try {
            Collection novels = NovelRepository.getInstance().getNovelsByOwnerID(user.getId());
            req.setAttribute("novels", novels);
        } catch (SQLException e) {
            resp.setStatus(500);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/view/personal/uploaded_novel.jsp").forward(req, resp);
    }
}
