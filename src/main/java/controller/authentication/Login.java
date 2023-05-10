package controller.authentication;

import core.JSON;
import model.Token;
import model.User;
import org.json.JSONException;
import repository.TokenRepository;
import repository.UserRepository;
import service.validator.TokenService;
import service.validator.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "LoginServlet", value = "/login")
public class Login extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            HttpSession session = request.getSession();

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            boolean remember = request.getParameter("remember") != null;

            // create a map to store invalid input values and error messages
            HashMap<String, String> errors;
            errors = UserValidator.getLoginInputError(username, password);
            if (!errors.isEmpty()) {
                response.getWriter().println(JSON.getResponseJson("error", errors));
                return;
            }
            try {
                if (remember) {
                    TokenRepository tokenRepository = TokenRepository.getInstance();

                    // generate random secure token
                    String plainToken = TokenService.generateTokenString();

                    //set token cookie
                    TokenService.setTokenCookie(response, plainToken);


                    // insert token to database
                    String hashedToken = TokenService.hashToken(plainToken);
                    User user = UserRepository.getInstance().getByUsername(username);
                    Token token = tokenRepository.createNewToken(user.getId(), hashedToken);
                    tokenRepository.insert(token);
                }
            } catch (SQLException e) {
                response.setStatus(500);
                Login.LOGGER.warning(e.getMessage());
                return;
            }

            session.setAttribute("username", username);
            session.setAttribute("password", password);

            response.getWriter().println(JSON.getResponseJson("success"));

        } catch (JSONException e) {
            response.setStatus(500);
            Login.LOGGER.warning(e.getMessage());
        }

    }
}