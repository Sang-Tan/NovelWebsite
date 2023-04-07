package controller;

import model.Novel;
import model.User;
import repository.NovelRepository;
import repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet(value = "/thanh-vien/*")
public class UserWallPage extends HttpServlet {
    private Integer getUserId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        //substring(1) to remove the first slash
        return Integer.parseInt(pathInfo.substring(1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User userInWall = UserRepository.getInstance().getById(getUserId(req));
            if (userInWall == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Collection<Novel> userNovels = NovelRepository.getInstance().getNovelsByOwnerID(userInWall.getId());
            req.setAttribute("userNovels", userNovels);
            req.setAttribute("userInWall", userInWall);
            req.getRequestDispatcher("/WEB-INF/view/user_wall.jsp").forward(req, resp);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }
}
