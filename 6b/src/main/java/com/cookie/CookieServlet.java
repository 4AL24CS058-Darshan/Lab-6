package com.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/cookie")
public class CookieServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");

        // Safety check
        if (name == null || name.trim().isEmpty()) {
            name = "Guest";
        }

        int count = 1;

        Cookie[] cookies = request.getCookies();
        Cookie visitCookie = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitCount")) {
                    visitCookie = c;
                }
            }
        }

        if (visitCookie != null) {
            count = Integer.parseInt(visitCookie.getValue());
            count++;
        }

        // ✅ Encode cookie value (fix for error)
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());

        // Create cookies
        Cookie nameCookie = new Cookie("username", encodedName);
        Cookie countCookie = new Cookie("visitCount", String.valueOf(count));

        nameCookie.setMaxAge(60 * 60);   // 1 hour
        countCookie.setMaxAge(60 * 60);

        response.addCookie(nameCookie);
        response.addCookie(countCookie);

        // Output response
        out.println("<html><body>");
        out.println("<h2>Welcome " + name + "!</h2>");
        out.println("<h3>You have visited this page " + count + " times.</h3>");
        out.println("<p>Cookie expiry time: 1 hour</p>");
        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Cookie[] cookies = request.getCookies();
        String name = "Guest";
        int count = 0;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    // ✅ Decode cookie value
                    name = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8.toString());
                }
                if (c.getName().equals("visitCount")) {
                    count = Integer.parseInt(c.getValue());
                }
            }
        }

        out.println("<html><body>");
        out.println("<h2>Welcome back " + name + "!</h2>");
        out.println("<h3>Visit count: " + count + "</h3>");
        out.println("<a href='index.html'>Go Back</a>");
        out.println("</body></html>");
    }
}