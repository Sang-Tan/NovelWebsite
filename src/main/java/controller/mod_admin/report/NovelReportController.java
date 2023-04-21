package controller.mod_admin.report;

import core.metadata.ReportSelection;
import repository.NovelReportRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/mod/bao-cao-truyen")
public class NovelReportController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("novelReportList", NovelReportRepository.getInstance().getAllNovelReport());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("selection", ReportSelection.NOVEL_REPORT);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/mod_admin/report/main_report_page.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
