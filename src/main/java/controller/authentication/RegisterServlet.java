package controller.authentication;

import model.User;
import core.Helper;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("viewFile", "register.jsp");
        request.setAttribute("pageTitle", "Register");
        Helper.view(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // retrieve user information from request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        // create a map to store invalid input values and error messages
        HashMap<String, String> errors = new HashMap<>();

        // add invalid input values and error messages to the map
        if (!UserValidator.isValidUsername(username)) {
            errors.put("username", "Username không hợp lệ");
        }
        if (!UserValidator.isUsernameExists(username)) {
            errors.put("username", "Username đã tồn tại");
        }
        if (!UserValidator.isValidPassword(password)) {
            errors.put("password", "Mật khẩu không hợp lệ");
        }
        if (!UserValidator.validateConfirmPassword(password, confirmPassword)) {
            errors.put("confirm_password", "Mật khẩu không khớp");
        }

        // store the error hashmap in the session
        HttpSession session = request.getSession();
        session.setAttribute("errors", errors);




        // create new user with information
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplayName(username);
        user.setActive(true);
        user.setRole("member");




        // set user information in session
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("username", user.getUsername());
        userMap.put("displayName", user.getDisplayName());
        userMap.put("avatar", user.getAvatar());
        userMap.put("role", user.getRole());
        userMap.put("isActive", user.isActive());

        session.setAttribute("user", userMap);

        // redirect to home page
        response.sendRedirect(request.getContextPath() + "/");
    }
}
