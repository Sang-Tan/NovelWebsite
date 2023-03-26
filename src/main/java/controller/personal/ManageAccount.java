package controller.personal;

import service.upload.FileUploadService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;

@MultipartConfig(maxFileSize = 1 * 1024 * 1024) //1MB
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

        if (avatar != null) {
            //TODO: change avatar if user has one, else create new one
        }

        
    }

    private HashMap<String, String> getErrors(String displayName, Part avatar) throws IOException {
        HashMap<String, String> errors = new HashMap<>();
        if (displayName == null || displayName.isEmpty()) {
            errors.put("display_name", "Tên hiển thị không được để trống");
        }
        if (avatar != null) {
            if (!FileUploadService.isImage(avatar)) {
                errors.put("avatar", "Ảnh đại diện không đúng định dạng");
            }
        }
        return errors;
    }
}
