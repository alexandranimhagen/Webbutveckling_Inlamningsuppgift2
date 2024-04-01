package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "StudentsServlet", urlPatterns = "/students")
public class StudentsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Sätt innehålletstyp till text/html
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DatabaseAccess.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            // Början av HTML-sidan
            out.println("<html><body>");
            out.println("<h2>All Students:</h2>");

            // Skapa en tabell för att visa studentdata
            out.println("<table border=\"1\">");
            out.println("<tr><th>ID</th><th>Name</th></tr>");

            // Loopa igenom resultatet från databasen och visa varje student i en rad
            while (rs.next()) {
                // Hämta studentdata från resultatet
                int studentId = rs.getInt("id");
                String studentName = rs.getString("name");

                // Skriv ut studentdata i tabellrader
                out.println("<tr><td>" + studentId + "</td><td>" + studentName + "</td></tr>");
            }
            out.println("</table>");

            // Länkar till andra servletar och hemsidan
            out.println("<br>");
            out.println("<a href=\"/demo_war_exploded/courses\">All Courses</a><br>");
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
