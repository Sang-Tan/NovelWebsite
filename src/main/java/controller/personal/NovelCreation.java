package controller.personal;

import io.github.cdimascio.dotenv.Dotenv;
import model.Novel;
import model.User;
import repository.GenreRepository;
import service.upload.NovelManageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig(maxFileSize = 1 * 1024 * 1024) //1MB
@WebServlet(value = "/ca-nhan/them-truyen")
public class NovelCreation extends HttpServlet {
    private final String IMG_DIR = Dotenv.load().get("COVER_UPLOAD_PATH");

    //render the create novel page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            List genres = GenreRepository.getInstance().getAll();
            request.setAttribute("genres", genres);

            request.getRequestDispatcher("/WEB-INF/view/personal/add_novel.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            User owner = (User) request.getAttribute("user");
            if (owner == null) {
                response.setStatus(401);
                return;
            }

            String novelName = request.getParameter("novel_name");
            String summary = request.getParameter("summary");
            String status = request.getParameter("status");

            //get genres in string and convert to int array
            String[] genres = request.getParameter("genres").split(",");
            int[] genreIds = new int[genres.length];
            for (int i = 0; i < genres.length; i++) {
                genreIds[i] = Integer.parseInt(genres[i]);
            }
            //get image
            Part image = request.getPart("image");

            Novel novelInfo = new Novel();
            novelInfo.setName(novelName);
            novelInfo.setSummary(summary);
            novelInfo.setStatus(status);

            String error = NovelManageService.validateUploadNovel(novelInfo, genreIds, image);
            if (error != null) {
                request.setAttribute("error", error);
                doGet(request, response);
                return;
            }


            NovelManageService.uploadNewNovel
                    (novelInfo, genreIds, image, owner.getId());
            response.sendRedirect("/ca-nhan");

        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }
}
