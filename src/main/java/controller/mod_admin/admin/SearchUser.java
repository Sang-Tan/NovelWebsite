package controller.mod_admin.admin;

import core.logging.BasicLogger;
import core.pagination.PageItem;
import core.pagination.Paginator;
import model.User;
import service.PagingService;
import service.admin.UserSelectResult;
import service.admin.UserSelector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/tim-thanh-vien")
public class SearchUser extends HttpServlet {
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doSearchUser(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private void doSearchUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ServletException {
        String searchText = Optional
                .ofNullable(req.getParameter("search-text"))
                .orElse("");
        String role = (req.getParameter("role") == null) ? "all" : req.getParameter("role");
        int page = 1;
        try {
            if (req.getParameter("page") != null) {
                page = Optional
                        .ofNullable(req.getParameter("page"))
                        .map(Integer::parseInt)
                        .orElse(1);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
        int offset = (page - 1) * PAGE_SIZE;
        UserSelector userSelector = new UserSelector();
        userSelector.usernameContain(searchText);

        switch (role) {
            case "all":
                break;
            case "admin":
                userSelector.roleMatch(User.ROLE_ADMIN);
                break;
            case "mod":
                userSelector.roleMatch(User.ROLE_MODERATOR);
                break;
            case "member":
                userSelector.roleMatch(User.ROLE_MEMBER);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        UserSelectResult result = userSelector.select(PAGE_SIZE, offset);
        Paginator paginator = new Paginator(result.totalUsers(), page, PAGE_SIZE);
        List<PageItem> pageItems = PagingService.getActivePageItems(5, paginator);

        req.setAttribute("pageItems", pageItems);
        req.setAttribute("selectedUsers", result.selectedUsers());

        req.getRequestDispatcher("/WEB-INF/view/mod_admin/admin/search_user.jsp")
                .forward(req, resp);
    }
}
