package controller.authentication;

import core.JSON;
import repository.TokenRepository;
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

@MultipartConfig
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {


    // Remove the user's credentials from the session
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");

        //delete token from database
        try {
            TokenRepository.getInstance().deleteToken(request.getParameter("token"));
        } catch (SQLException e) {
            response.setStatus(500);
            Logout.LOGGER.warning("token not found");
        }

        // Expire the token cookie
        Cookie tokenCookie = new Cookie("token", "");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);

    }


}