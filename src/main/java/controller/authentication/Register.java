package controller.authentication;

import com.mysql.cj.log.Log;
import core.SHA256Hashing;
import core.validator.UserValidator;
import repository.UserRepository;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;

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

            HashMap<String, String> errors = getInputError(username, password, confirmPassword);
            if (!errors.isEmpty()) {
                response.getWriter().println(getResponseJson("error", errors));
                return;
            }

            // create new user with information
            User user = UserRepository.getInstance().createNewUser(username, password);

            // add user to database
            try {
                UserRepository.getInstance().insert(user);
            } catch (Exception e) {
                response.setStatus(500);
                return;
            }

            // set user information in session
            User createdUser = UserRepository.getInstance().getByUsername(username);
            if (createdUser == null) {
                response.setStatus(500);
                Register.LOGGER.warning(String.format("User %s not found", username));
            }
            int userID = createdUser.getId();
            session.setAttribute("userID", userID);
            response.getWriter().println(getResponseJson("success"));
        } catch (JSONException | SQLException e) {
            response.setStatus(500);
            Register.LOGGER.warning(e.getMessage());
        }
    }

    private HashMap<String, String> getInputError(String username, String password, String confirmPassword) {
        try {
            // create a map to store invalid input values and error messages
            HashMap<String, String> errors = new HashMap<>();

            // add invalid input values and error messages to the map
            // validate username
            if (username == null || username.isEmpty()) {
                errors.put("username", "Username không được để trống");
            } else if (!UserValidator.isValidUsername(username)) {
                errors.put("username", "Username không hợp lệ");
            } else if (UserValidator.isUsernameExists(username)) {
                errors.put("username", "Username đã tồn tại");
            }

            // validate password
            if (password == null || password.isEmpty()) {
                errors.put("password", "Mật khẩu không được để trống");
            } else if (!UserValidator.isValidConfirmPassword(password, confirmPassword)) {
                errors.put("confirm_password", "Mật khẩu không khớp");
            } else if (!UserValidator.isValidPassword(password)) {
                errors.put("password", "Mật khẩu không hợp lệ");
            }
            

            return errors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<String, String>();
    }

    /**
     * Get response json
     *
     * @param status status want to response
     * @param errors errors want to response
     * @return
     * @throws JSONException if the value cannot be converted to JSON
     */
    private JSONObject getResponseJson(String status, HashMap<String, String> errors) throws JSONException {
        JSONObject responseJson = new JSONObject();
        if (!errors.isEmpty()) {
            responseJson.put("status", status);
            responseJson.put("errors", errors);
            return responseJson;
        } else {
            responseJson.put("status", status);
            return responseJson;
        }
    }


    private JSONObject getResponseJson(String status) throws IOException, JSONException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", status);
        return responseJson;

    }


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

