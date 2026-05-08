package com.factorial;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/factorial")
public class FactorialServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            int number = Integer.parseInt(request.getParameter("number"));

            if (number < 0) {
                throw new IllegalArgumentException("Number cannot be negative");
            }

            if (number > 20) {
                throw new IllegalArgumentException("Number too large, max allowed is 20");
            }

            long factorial = 1;
            String calculation = "Calculation steps:<br>";

            if (number == 0 || number == 1) {
                calculation += number + "! = 1";
            } else {
                calculation += number + "! = " + number;

                for (int i = number - 1; i >= 1; i--) {
                    factorial *= (i + 1);
                    calculation += " × " + i;
                }
                factorial *= 1;
                calculation += " = " + factorial;
            }

            out.println("<html><head><title>Factorial Result</title></head><body>");
            out.println("<h2>Factorial Result</h2>");
            out.println("<p>Number: " + number + "</p>");
            out.println("<p>Factorial: " + factorial + "</p>");
            out.println("<p>" + calculation + "</p>");
            out.println("<a href='index.html'>Try Again</a>");
            out.println("</body></html>");

        } catch (NumberFormatException e) {
            error(out, "Please enter a valid number");
        } catch (Exception e) {
            error(out, e.getMessage());
        }
    }

    private void error(PrintWriter out, String msg) {
        out.println("<html><body style='color:red;'>");
        out.println("<h2>Error</h2>");
        out.println("<p>" + msg + "</p>");
        out.println("<a href='index.html'>Back</a>");
        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}