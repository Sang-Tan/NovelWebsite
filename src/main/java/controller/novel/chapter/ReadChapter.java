package controller.novel.chapter;

import controller.URIHandler;
import core.StringCoverter;
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
            int chapterID = URIHandler.getIdFromPathComponent(part);
            Chapter chapter = ChapterRepository.getInstance().getById(chapterID);
            String chapterUri = ChapterRepository.getInstance().generatePathComponent(chapterID);
            if(chapterID == -1) {
                response.setStatus(404);// not found
                return;
            }
            else if(!chapterUri.equals(part)) {
                response.sendRedirect(chapterUri);
                return;
            }

            Chapter nextChapter = ChapterRepository.getInstance().getNextChapter(chapterID);
            Chapter previousChapter = ChapterRepository.getInstance().getPreviousChapter(chapterID);

            // guess cannot read chapter if not approved
            User user = (User) request.getAttribute("user");
            if(!chapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_APPROVED)){
                if(user == null || user.getRole().equals(User.ROLE_MEMBER))
                {
                    response.setStatus(401);// unauthorized
                    return;
                }
                // admin and moderator can read chapter even if not approved



            }

            if(user != null)

            if(!chapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_APPROVED)
                || user.getRole().equals( User.ROLE_ADMIN)
                || user.getRole().equals(User.ROLE_MODERATOR))
            {
                response.setStatus(401);// unauthorized
                return;
            }

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
