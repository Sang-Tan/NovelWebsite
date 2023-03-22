package controller.authentication;

import database.UserRepository;
import model.User;
import core.Helper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.setAttribute("viewFile", "register.jsp");
//        request.setAttribute("pageTitle", "Register");
//        Helper.view(request, response);
//    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            JSONObject responseJson = new JSONObject();
            HttpSession session = request.getSession();

            // retrieve user information from request parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");

            // create a map to store invalid input values and error messages
            HashMap<String, String> errors = new HashMap<>();

            // add invalid input values and error messages to the map
            // validate username
            if (username == null || username.isEmpty()) {
                errors.put("username", "Username không được để trống");
            } else if (!UserValidator.isValidUsername(username)) {
                errors.put("username", "Username không hợp lệ");
            } else if (!UserValidator.isUsernameExists(username)) {
                errors.put("username", "Username đã tồn tại");
            }

            // validate password
            if (password == null || password.isEmpty()) {
                errors.put("password", "Mật khẩu không được để trống");
            } else if (!UserValidator.isValidPassword(password)) {
                errors.put("password", "Mật khẩu không hợp lệ");
            } else if (!UserValidator.validateConfirmPassword(password, confirmPassword)) {
                errors.put("confirm_password", "Mật khẩu không khớp");
            }
            if (!errors.isEmpty()) {
                responseJson.put("status", "error");
                responseJson.put("errors", errors);
                response.getWriter().println(responseJson);
                return;
            }


            // create new user with information
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setDisplayName(username);
            user.setActive(true);
            user.setRole("member");
            try {
                UserRepository.add(user);
            } catch (Exception e) {
                response.setStatus(500);
                return;
            }


            // set user information in session
            Integer userID = UserRepository.getByUsername(username).getId();

            responseJson.put("status", "success");

            response.getWriter().println(responseJson);
        } catch (JSONException e) {
            response.setStatus(500);
        }
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

