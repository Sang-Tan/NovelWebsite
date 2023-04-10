package controller.novel.chapter;

import model.Chapter;
import model.User;
import repository.ChapterRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "ReadNovelServlet", value = "/doc-tieu-thuyet/*")
public class ReadChapter extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ReadChapter.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {


            String pathInfo = request.getPathInfo();
            String part = pathInfo.split("/")[2];
            int chapterID = Integer.parseInt(part.substring(0, part.indexOf("-")));
            Chapter chapter = ChapterRepository.getInstance().getById(chapterID);
            Chapter nextChapter = ChapterRepository.getInstance().getNextChapter(chapterID);
            Chapter previousChapter = ChapterRepository.getInstance().getPreviousChapter(chapterID);
            User user = (User) request.getSession().getAttribute("user");
            if(!chapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_APPROVED)
                || user.getRole() == User.ROLE_ADMIN
                || user.getRole() == User.ROLE_MODERATOR)
                throw new Exception("Chapter is not approved yet");
            if(nextChapter !=null && !nextChapter.getApprovalStatus().equals("approved"))
                nextChapter = null;
            if(previousChapter != null && !previousChapter.getApprovalStatus().equals("approved"))
                previousChapter = null;
            String novelUrl = pathInfo.split("/")[1];
            request.setAttribute("novelUrl", novelUrl);
            request.setAttribute("chapter", chapter);
            request.setAttribute("nextChapter", nextChapter);
            request.setAttribute("previousChapter", previousChapter);



            request.getRequestDispatcher("/WEB-INF/view/reading.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            ReadChapter.LOGGER.warning(e.getMessage());

        }
    }
}
