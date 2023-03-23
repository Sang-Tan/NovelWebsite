package controller;

import com.mysql.cj.conf.ConnectionUrlParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

@MultipartConfig
@WebServlet(name = "TestUI", value = "/testui/*")
public class TestUI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String URI = request.getRequestURI();
        String uriParameter = URI.substring(URI.lastIndexOf("/testui/") + 8);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/" + uriParameter + ".jsp");

        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("application/x-www-form-urlencoded")) {
            ArrayList<Part> parts = (ArrayList<Part>) request.getParts();


        } else {
            //get parameters
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                String value = request.getParameter(name);
                out.println(name + " = " + value);
            }
        }


    }

}
