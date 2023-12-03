import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//SalesOrder and ReplenishOrder are repeating code
// but its cleaner and easier than making the table name an additional parameter
public class ReplenishOrderDb {

    public static void insert(Connection connection, int replenish_id, String shipping_option, String tracking_num, String supplier_email) {
        try {
            String sql = "INSERT INTO ReplenishOrder(replenish_id, shipping_option, tracking_num, supplier_email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, replenish_id);
            statement.setString(2, shipping_option);
            statement.setString(3, tracking_num);
            statement.setString(4, supplier_email);
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
     * @param replenish_id
     */
    public static void delete(Connection connection, int replenish_id) {
        try {
            String sql = "DELETE FROM ReplenishOrder WHERE replenish_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, replenish_id);

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
     * @param replenish_id
     * @param colName
     * @param updatedData
     */
    public static void update(Connection connection, int replenish_id, String colName, String updatedData) {
        try {
            String sql = "UPDATE ReplenishOrder SET " + colName + " = ? WHERE replenish_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedData);
            statement.setInt(2, replenish_id);

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

    static public ResultSet getAll(Connection connection) {

        String sql = "SELECT * FROM ReplenishOrder_All_Info";

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