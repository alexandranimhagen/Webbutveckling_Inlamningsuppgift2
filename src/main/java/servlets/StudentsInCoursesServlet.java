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

// Servlet för att visa närvarotabellen för studenter i kurser
@WebServlet(name = "StudentsInCoursesServlet", urlPatterns = "/attendance")
public class StudentsInCoursesServlet extends HttpServlet {

    // Hanterar GET-begäran för att visa närvarotabellen
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Sätt innehållestyp till text/html
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DatabaseAccess.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM attendance")) {

            // Början av HTML-sidan
            out.println("<html><body>");
            out.println("<h2>Attendance Table:</h2>");

            // Skapa en tabell för att visa närvarotabellen
            out.println("<table border=\"1\">");
            out.println("<tr><th>ID</th><th>Student ID</th><th>Course ID</th></tr>");

            // Loopa igenom resultatet från databasen och visa varje närvarorad i en rad
            while (rs.next()) {
                // Hämta närvarodata från resultatet
                int attendanceId = rs.getInt("id");
                int studentId = rs.getInt("student_id");
                int courseId = rs.getInt("course_id");

                // Skriv ut närvarodata i tabellrader
                out.println("<tr><td>" + attendanceId + "</td><td>" + studentId + "</td><td>" + courseId + "</td></tr>");
            }
            out.println("</table>");

            // Länkar till andra servletar och hemsidan
            out.println("<br>");
            out.println("<a href=\"/demo_war_exploded/students\">All Students</a><br>");
            out.println("<a href=\"/demo_war_exploded/courses\">All Courses</a><br>");
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
