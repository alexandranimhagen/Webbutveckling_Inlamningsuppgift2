package servlets;

import java.sql.*;

// Klass för att hantera åtkomst till databasen
public class DatabaseAccess {

    // Metod för att få en JDBC-anslutning till databasen
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Ladda MySQL JDBC-drivrutinen
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Ange JDBC-anslutnings-URL, användarnamn och lösenord för att ansluta till databasen
            String jdbcUrl = "jdbc:mysql://localhost:3306/gritacademy";
            String username = "AlexandraNimhagen";
            String password = "z/uUHlSeE!tP(gF7";
            // Skapa en anslutning till databasen
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException e) {
            // Om MySQL JDBC-drivrutinen inte hittas, skriv ut felmeddelande och stacktrace
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Om anslutningen till databasen misslyckas, skriv ut felmeddelande och stacktrace
            System.err.println("Unable to connect to the database.");
            e.printStackTrace();
        }
        // Returnerar den skapade anslutningen till databasen
        return connection;
    }
}
