package controller.authentication;

import core.JSON;
import repository.TokenRepository;
import service.validator.TokenService;
import service.validator.UserValidator;
import repository.UserRepository;
import model.User;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class Logout extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Logout.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Remove the user's credentials from the session
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");

        // Remove the user's token from the database and the cookie if it exists
        Cookie[] cookies = request.getCookies();
        Cookie cookie = TokenService.getTokenCookie(cookies);
        if (cookie != null) {
            //delete token from database
            try {
                TokenRepository.getInstance().deleteToken(cookie.getValue());
            } catch (SQLException e) {
                response.setStatus(500);
                Logout.LOGGER.warning("token not found");
            }
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        response.sendRedirect("/");
    }

}