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

@WebServlet("/ca-nhan")
public class Main extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User user = (User) req.getAttribute("user");
            Integer uploadedNovelCount = NovelRepository.getInstance().getNovelsByOwnerID(user.getId()).size();
            req.setAttribute("uploadedNovelCount", uploadedNovelCount);
            req.getRequestDispatcher("/WEB-INF/view/personal/index.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
    }
}
