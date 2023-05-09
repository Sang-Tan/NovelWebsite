package controller.novel.chapter;

import core.StringUtils;
import core.logging.BasicLogger;
import model.Chapter;
import model.Novel;
import model.User;
import model.intermediate.ChapterMark;
import repository.ChapterRepository;
import repository.intermediate.ChapterMarkRepository;
import service.BookmarkService;
import service.ChapterService;
import service.URLSlugification;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet(name = "ReadNovelServlet", value = "/doc-tieu-thuyet/*")
public class ReadChapter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String novelPathComponent = pathInfo.split("/")[1];
            String chapterPathComponent = pathInfo.split("/")[2];
            int chapterID = StringUtils.extractFirstInt(chapterPathComponent);

            Chapter chapter = ChapterRepository.getInstance().getById(chapterID);
            Novel novel = chapter.getBelongVolume().getBelongNovel();

            String chapterUri = chapterID + "-" + URLSlugification.sluging(chapter.getName());
            String novelUri = novel.getId() + "-" + URLSlugification.sluging(novel.getName());
            if (chapterID == -1) {
                response.setStatus(404);// not found
                return;
            } else if (!novelUri.equals(novelPathComponent) || !chapterUri.equals(chapterPathComponent)) {
                String param = request.getQueryString();
                response.sendRedirect(String.format("../%s/%s%s", novelUri, chapterUri, param == null ? "" : "?" + param));
                return;
            }
            User user = (User) request.getAttribute("user");
            if (!chapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_APPROVED) || !novel.getApprovalStatus().equals(Novel.APPROVE_STATUS_APPROVED)) {
                // member cannot read chapter if not approved
                if (user == null || user.getRole().equals(User.ROLE_MEMBER)) {
                    response.setStatus(401);// unauthorized
                    return;
                }
                // admin and moderator can read chapter even if not approved
            }


            Chapter nextChapter = ChapterService.getNextApprovedChapter(chapterID);
//            Chapter nextChapter = ChapterRepository.getInstance().getNextChapter(chapterID);
//            if (nextChapter != null)
//                while (nextChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_PENDING) || nextChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_REJECTED)) {
//                    nextChapter = ChapterRepository.getInstance().getNextChapter(nextChapter.getId());
//                    if(nextChapter == null) break;
//                }
            Chapter previousChapter = ChapterService.getPreviousApprovedChapter(chapterID);
//            if (previousChapter != null)
//                while (previousChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_PENDING) || previousChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_REJECTED)) {
//                    previousChapter = ChapterRepository.getInstance().getPreviousChapter(previousChapter.getId());
//                    if(previousChapter == null) break;
//                }


            request.setAttribute("reqNovel", novel);
            request.setAttribute("reqChapter", chapter);
            request.setAttribute("nextChapter", nextChapter);
            request.setAttribute("previousChapter", previousChapter);
            ChapterMark chapterMark = ChapterMarkRepository.getInstance().getChapterMark(Integer.valueOf(user.getId()), Integer.valueOf(chapter.getId()));
            request.setAttribute("isBookMarkYet", chapterMark == null ? false : true);


            request.getRequestDispatcher("/WEB-INF/view/reading.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();

        }
    }
}
