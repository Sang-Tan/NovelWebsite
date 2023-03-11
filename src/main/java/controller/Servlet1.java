package controller;

import service.IObjectService;
import service.ObjectService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Servlet1", value = "/servlet") //phần value sẽ đổi tên vì nó là phần hiển thị trên thanh URL.
public class Servlet1 extends HttpServlet {
    private final IObjectService objectService = new ObjectService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // phần các method theo phương thức get
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // phần các method theo phương thức post
    }
}
