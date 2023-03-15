package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "TestUI", value = "/testui/*")
public class TestUI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String uriParameter = URI.substring(URI.lastIndexOf("/") + 1);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/" + uriParameter + ".jsp");

        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
