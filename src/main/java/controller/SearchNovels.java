package controller;

import core.JSON;
import model.Token;
import model.User;
import org.json.JSONException;
import repository.TokenRepository;
import repository.UserRepository;
import service.validator.TokenService;
import service.validator.UserValidator;

import javax.servlet.ServletException;
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

@WebServlet(name = "SearchNovelsServlet", value = "/search-novels")
public class SearchNovels extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SearchNovels.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    private HashMap<String, String> getInputError(String username, String password) {
        try {
            // create a map to store invalid input values and error messages
            HashMap<String, String> errors = new HashMap<>();

            // add invalid input values and error messages to the map
            // validate username
            if (username == null || username.isEmpty()) {
                errors.put("username", "Username không được để trống");
            } else if (!UserValidator.isUsernameExists(username)) {
                errors.put("username", "Username không tồn tại");
            } else if (password == null || password.isEmpty()) {
                errors.put("password", "Mật khẩu không được để trống");
            } else {
                if (!UserValidator.credentialVerify(username, password)) {
                    errors.put("password", "Mật khẩu không đúng");
                }
            }
            return errors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
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
            errors = getInputError(username, password);
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
                SearchNovels.LOGGER.warning(e.getMessage());
                return;
            }

            session.setAttribute("username", username);
            session.setAttribute("password", password);

            response.getWriter().println(JSON.getResponseJson("success"));

        } catch (JSONException e) {
            response.setStatus(500);
            SearchNovels.LOGGER.warning(e.getMessage());
        }

    }
}