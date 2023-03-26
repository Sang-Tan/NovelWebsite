package controller.personal;

import io.github.cdimascio.dotenv.Dotenv;
import repository.GenreRepository;
import service.upload.FileUploadService;

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
@WebServlet(value = "/personal/add-novel")
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
            String novelName = request.getParameter("novel_name");
            String summary = request.getParameter("summary");
            String status = request.getParameter("status");

            //get genres in string and convert to int array
            String[] genres = request.getParameter("genres").split(",");
            Integer[] genreIds = new Integer[genres.length];
            for (int i = 0; i < genres.length; i++) {
                genreIds[i] = Integer.parseInt(genres[i]);
            }
            //get image
            Part image = request.getPart("image");

            if (getError(novelName, summary, status, genreIds, image) != null) {
                request.setAttribute("error", getError(novelName, summary, status, genreIds, image));
                doGet(request, response);
                return;
            }
            String imageName = FileUploadService.randomFileName(IMG_DIR, image.getSubmittedFileName());
            FileUploadService.uploadFile(imageName, image);
            /*try {
                //TODO: implement method to create novel into database

            } catch (SQLException e) {
                //delete image if cannot create novel
                FileUploadService.deleteFile(imageName);
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getError(String novelName, String summary, String status, Integer[] genres, Part image) throws IOException {
        if (novelName == null || novelName.isEmpty()) {
            return "Tên truyện không được để trống";
        }
        if (summary == null || summary.isEmpty()) {
            return "Mô tả không được để trống";
        }
        if (status == null || status.isEmpty()) {
            return "Trạng thái không được để trống";
        }
        if (genres == null || genres.length == 0) {
            return "Thể loại không được để trống";
        }
        if (image == null || image.getSize() == 0) {
            return "Ảnh bìa không được để trống";
        }

        if (!FileUploadService.isImage(image)) {
            return "Ảnh bìa không hợp lệ";
        }
        return null;
    }
}
