package controller.personal;

import core.metadata.ManageNovelAction;
import model.Novel;
import repository.NovelRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ca-nhan/tieu-thuyet/*")
public class ManageNovel extends HttpServlet {
    private int getNovelId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        //substring(1) to remove the first slash
        return Integer.parseInt(pathInfo.substring(1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int novelId = getNovelId(req);
        try {
            Novel novel = NovelRepository.getInstance().getById(novelId);
            if (novel == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("novel", novel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("action", ManageNovelAction.EDIT_NOVEL);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }
}
