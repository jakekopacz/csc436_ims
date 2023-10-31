import java.sql.*;

public class DatabaseConnection {


    public static Connection getConnection(String nameDB) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDB, "root", "nobadsongs");
            System.out.println("Connected to database: " + nameDB);
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return conn;
    }


    public static void closeConnection(Connection conn) {

        try {
            if(conn != null) {
                conn.close();
            }
            System.out.println("Disconnected from database");
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}