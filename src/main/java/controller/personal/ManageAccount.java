package controller.personal;

import core.FileUtil;
import model.User;
import repository.UserRepository;
import service.upload.AvatarUploadService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@MultipartConfig(maxFileSize = 1024 * 1024) //1MB
@WebServlet("/ca-nhan/thong-tin")
public class ManageAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/view/personal/manage_info.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String displayName = req.getParameter("display_name");

        Part avatar = req.getPart("avatar");

        HashMap<String, String> errors = getErrors(displayName, avatar);
        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
            doGet(req, resp);
            return;
        }
        User user = (User) req.getAttribute("user");

        user.setDisplayName(displayName);
        if (avatar != null && avatar.getSize() > 0) {
            AvatarUploadService.uploadAvatar(user, avatar);
        }

        try {
            UserRepository.getInstance().update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("/ca-nhan/thong-tin");

    }

    private HashMap<String, String> getErrors(String displayName, Part avatar) throws IOException {
        HashMap<String, String> errors = new HashMap<>();
        if (displayName == null || displayName.isEmpty()) {
            errors.put("display_name", "Tên hiển thị không được để trống");
        }
        if (avatar != null && avatar.getSize() > 0) {
            if (!FileUtil.isImage(avatar.getInputStream())) {
                errors.put("avatar", "Ảnh đại diện không đúng định dạng");
            }
        }
        return errors;
    }
}
