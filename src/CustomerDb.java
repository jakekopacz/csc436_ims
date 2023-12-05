import java.sql.*;

public class CustomerDb {

    public static void insert(Connection connection, String email, String card) {
        try {
            String sql = "INSERT INTO Customer (email, credit_card) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, card);

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void MailingList(Connection connection, String customer_email, String addy, String city, String state, String p_code ) {
        try {
            String sql = "INSERT INTO MailingList (customer_email, supplier_email, address_1, address_2, address_3, city, state, country, postal_code) VALUES (?, NULL, ?, NULL, NULL, ?, ?, 'US', ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer_email);
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

        String sql = "SELECT * FROM customer_details";

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
}
