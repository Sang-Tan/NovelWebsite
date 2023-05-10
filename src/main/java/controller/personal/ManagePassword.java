package controller.personal;


import core.logging.BasicLogger;
import model.User;
import service.validator.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ca-nhan/doi-mat-khau")
public class ManagePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/personal/manage_password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("old-password");
        String newPassword = req.getParameter("new-password");
        String confirmPassword = req.getParameter("confirm-password");
        User user = (User) req.getAttribute("user");

        try {
            String errorMessage = UserService.changePassword(user, oldPassword, newPassword, confirmPassword);
            if (errorMessage != null) {
                req.setAttribute("errorMessage", errorMessage);
            } else {
                req.setAttribute("successMessage", "Đổi mật khẩu thành công");
            }
            doGet(req, resp);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }

    }
}
