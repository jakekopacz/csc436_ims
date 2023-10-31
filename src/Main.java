import java.sql.*;

public class Main {
    public static void main(String[] args) {

        Connection conn = DatabaseConnection.getConnection("Inventory");

        DatabaseConnection.closeConnection(conn);
        System.out.println("HI");
    }
}