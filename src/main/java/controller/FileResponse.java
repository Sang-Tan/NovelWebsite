package controller;

import service.upload.FileUploadService;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "FileResponse", value = "/uploads/*")
public class FileResponse extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filePath = req.getPathInfo();

        File downloadFile = FileUploadService.getFile(filePath);
        if (!downloadFile.exists()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //open input stream to read file
        FileInputStream fileStream = new FileInputStream(downloadFile);

        ServletContext context = getServletContext();

        String mimeType = context.getMimeType(downloadFile.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());

        /*String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        resp.setHeader(headerKey, headerValue);*/

        String contentType = getServletContext().getMimeType(filePath);
        resp.setContentType(contentType);

        OutputStream outStream = resp.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = fileStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        fileStream.close();
        outStream.close();
    }
}
