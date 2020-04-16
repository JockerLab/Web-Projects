package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] uriArray = uri.split("[+]");
        boolean setContentType = false;
        for (String currentUri : uriArray) {
            String absolutePath = getServletContext().getRealPath(".") + "/../../src/main/webapp/static";
            File file1 = new File(absolutePath, currentUri);
            if (file1.isFile()) {
                makeResponse(request, response, currentUri, file1, setContentType);
                setContentType = true;
            } else {
                File file2 = new File(getServletContext().getRealPath("/static"), currentUri);
                if (file2.isFile()) {
                    makeResponse(request, response, currentUri, file2, setContentType);
                    setContentType = true;
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    private void makeResponse(HttpServletRequest request, HttpServletResponse response, String uri, File file, boolean flag) throws IOException {
        if (!flag) {
            response.setContentType(getContentTypeFromName(uri));
        }
        OutputStream outputStream = response.getOutputStream();
        Files.copy(file.toPath(), outputStream);
        outputStream.flush();
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
