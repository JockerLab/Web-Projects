package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    private int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(900) + 100;
    }

    private String captchaHtml(byte[] image) {
        String imageHtml = Base64.getEncoder().encodeToString(image);
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Captcha</title>\n" +
                "</head>\n" +
                "<body style=\"text-align: center; margin-top: 10%\">\n" +
                "    <img src=\"data:image/png;base64, " + imageHtml + "\"/>\n" +
                "    <form action=\"\">\n" +
                "        <label for=\"captcha\"><p style=\"font-size: 1.5rem; color: #666; font-family: \"Arial Narrow\", sans-serif\">Enter captcha: </p></label>\n" +
                "        <input name=\"captcha\" id=\"captcha\" style=\"border: 1px solid rgb(185, 185, 185); border-radius: 6px\">\n" +
                "        <input type=\"submit\" value=\"Submit\">" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
    }

    private void makeNewCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain chain, HttpSession session) throws IOException, ServletException {
        session.setAttribute("passedCaptcha", false);
        int currentRandomNumber = getRandomNumber();
        session.setAttribute("expect", Integer.toString(currentRandomNumber));
        byte[] imageHtml = ImageUtils.toPng(Integer.toString(currentRandomNumber));
        String form = captchaHtml(imageHtml);
        DelayedHttpServletResponse delayedResponse = new DelayedHttpServletResponse(response);
        response.getWriter().print(form);
        response.getWriter().flush();
        chain.doFilter(request, delayedResponse);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String expectNumber = (String) session.getAttribute("expect");
        String actualNumber = request.getParameter("captcha");
        if (expectNumber == null) {
            makeNewCaptcha(request, response, chain, session);
        } else {
            if (expectNumber.equals(actualNumber)) {
                session.setAttribute("passedCaptcha", true);
                response.sendRedirect(request.getRequestURI());
            } else {
                if ((Boolean) session.getAttribute("passedCaptcha")) {
                    chain.doFilter(request, response);
                } else {
                    makeNewCaptcha(request, response, chain, session);
                }
            }
        }
    }
}