package controller.authentication;

import core.JSON;
import model.User;
import org.json.JSONException;
import service.validator.UserService;
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
@WebServlet(name = "RegisterServlet", value = "/register")
public class Register extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Register.class.getName());

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
        try {
            response.setContentType("application/json");
            HttpSession session = request.getSession();

            // retrieve user information from request parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");

            HashMap<String, String> errors = UserValidator.getRegisterInputError(username, password, confirmPassword);
            if (!errors.isEmpty()) {
                response.getWriter().println(JSON.getResponseJson("error", errors));
                return;
            }

            // create new user with information
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            UserService.insertNewUser(user);

            response.getWriter().println(JSON.getResponseJson("success"));
        } catch (JSONException | SQLException e) {
            response.setStatus(500);
            Register.LOGGER.warning(e.getMessage());
        }
    }


    /**
     * Get response json
     *
     * @param status status want to response
     * @param errors errors want to response
     * @return
     * @throws JSONException if the value cannot be converted to JSON
     */
}
/*
{
    "status" : "success",
}

{
    "status" : "error",
    "errors" : {
        "username" : "Username không hợp lệ",
        "password" : "Mật khẩu không hợp lệ",
        "confirm_password" : "Mật khẩu không khớp"
    }
}
*/