package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "CoursesServlet", urlPatterns = "/courses")
public class CoursesServlet extends HttpServlet {

    // Hanterar GET-begäran för att visa alla kurser
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Sätt innehållestyp till text/html
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DatabaseAccess.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses")) {

            // Början av HTML-sidan
            out.println("<html><body>");
            out.println("<h2>All Courses:</h2>");

            // Skapa en tabell för att visa kurserna
            out.println("<table border=\"1\">");
            out.println("<tr><th>ID</th><th>Name</th><th>Description</th></tr>");

            // Loopa igenom resultatet från databasen och visa varje kurs i en rad
            while (rs.next()) {
                // Hämta kursdata från resultatet
                int courseId = rs.getInt("id");
                String courseName = rs.getString("name");
                String courseDescription = rs.getString("description");

                // Skriv ut kursdata i tabellrader
                out.println("<tr><td>" + courseId + "</td><td>" + courseName + "</td><td>" + courseDescription + "</td></tr>");
            }
            out.println("</table>");

            // Länkar till andra servletar och hemsidan
            out.println("<br>");
            out.println("<a href=\"/demo_war_exploded/students\">All Students</a><br>");
            out.println("<a href=\"/demo_war_exploded/attendance\">Attendance</a><br>");
            out.println("<a href=\"/demo_war_exploded/\">Home</a><br>");

            // Slut på HTML-sidan
            out.println("</body></html>");
        } catch (SQLException e) {
            // Vid fel skrivs felmeddelandet ut och eventuella stacktrace sparas
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    // Hanterar POST-begäran
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
