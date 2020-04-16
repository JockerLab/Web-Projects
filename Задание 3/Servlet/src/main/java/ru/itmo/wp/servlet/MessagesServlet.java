package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import ru.itmo.wp.util.UserMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class MessagesServlet extends HttpServlet {
    private ArrayList<UserMessage> messageArray = new ArrayList<>();

    private void doAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String userName = request.getParameter("user");
        if (userName != null) {
            session.setAttribute("user", userName);
        }
        String json = new Gson().toJson(userName);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userName = (String) session.getAttribute("user");
        String message = request.getParameter("text");
        messageArray.add(new UserMessage(userName, message));
    }

    private void doFindAll(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String json = new Gson().toJson(messageArray);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        response.setCharacterEncoding("windows-1251");
        if ("/message/auth".equals(uri)) {
            doAuth(request, response, session);
        }
        if ("/message/add".equals(uri)) {
            doAdd(request, response, session);
        }
        if ("/message/findAll".equals(uri)) {
            doFindAll(request, response, session);
        }
        response.setContentType("application/json");
    }
}
