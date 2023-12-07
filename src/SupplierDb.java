import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDb {

    public static void insert(Connection connection, String email, Double account_balance, int lead_time) {
        try {
            String sql = "INSERT INTO Supplier (email, account_balance, lead_time) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setDouble(2, account_balance);
            statement.setInt(3,lead_time);

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void MailingList(Connection connection, String supplier_email, String addy, String city, String state, String p_code ) {
        try {
            String sql = "INSERT INTO MailingList (customer_email, supplier_email, address_1, address_2, address_3, city, state, country, postal_code) VALUES (NULL, ?, ?, NULL, NULL, ?, ?, 'US', ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, supplier_email);
            statement.setString(2, addy);
            statement.setString(3, city);
            statement.setString(4, state);
            statement.setString(5, p_code);



            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public ResultSet getAll(Connection connection) {

        String sql = "SELECT email FROM Supplier";

        try {
            PreparedStatement pstmnt = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pstmnt.executeQuery();

//            rs.close();
//            pstmnt.close();
            System.out.println("Search GOOD");
            return rs;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    static public ResultSet getAllCols(Connection connection) {

        String sql = "SELECT * FROM Supplier";

        try {
            PreparedStatement pstmnt = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pstmnt.executeQuery();

//            rs.close();
//            pstmnt.close();
            System.out.println("Search GOOD");
            return rs;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static void removeSupplier(Connection conn, String email) {

        //removeSupplier(conn, item_id);

        try {
            String sql = "DELETE FROM Supplier WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);

            int rowsAffected = statement.executeUpdate();
            conn.commit();
            if (rowsAffected > 0) {
                System.out.println("Item removed successfully.");
            } else {
                System.out.println("Item not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
