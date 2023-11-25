
import java.sql.*;

public class SalesOrderDb {

    public static void insert(Connection connection, int order_id, String shipping_option, String tracking_num, String customer_email) {
        try {
            String sql = "INSERT INTO SalesOrder(order_id, shipping_option, tracking_num, customer_email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_id);
            statement.setString(2, shipping_option);
            statement.setString(3, tracking_num);
            statement.setString(4, customer_email);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * delete by order_id
     * NOTE: When using this also delete all instances in OrderItem (will be managed in DatabaseManager)
     *
     * @param connection
     * @param order_id
     */
    public static void delete(Connection connection, int order_id) {
        try {
            String sql = "DELETE FROM SalesOrder WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_id);

            int rowsAffected = statement.executeUpdate();
            connection.commit();

            if (rowsAffected > 0) {
                System.out.println("Item removed successfully.");
            } else {
                System.out.println("Item not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * update column Value by order_id
     *
     * @param connection
     * @param order_id
     * @param colName
     * @param updatedData
     */
    public static void update(Connection connection, int order_id, String colName, String updatedData) {
        try {
            String sql = "UPDATE SalesOrder SET " + colName + " = ? WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedData);
            statement.setInt(2, order_id);

            int rowsAffected = statement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Item modified successfully");
            } else {
                System.out.println("Item not found.");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}