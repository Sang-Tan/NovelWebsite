package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet1", value = "/home") //phần value sẽ đổi tên vì nó là phần hiển thị trên thanh URL.
public class Servlet1 extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // phần các method theo phương thức get
//        System.out.println("Servlet1");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action){
            case "create": //điền action tùy theo link URL
                //gọi hàm
                break;
                
            default:
                showHomePage(request, response);
                break;
        }
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/landing.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // phần các method theo phương thức post
    }
}
