
import java.sql.*;

public class ItemOrderDb {

    public static void insert(Connection connection, int order_id, int item_id, int quantity) {
        try {
            String sql = "INSERT INTO ItemOrder (order_id, item_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_id);
            statement.setInt(2, item_id);
            statement.setInt(3, quantity);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete specific item in an order
     * @param connection
     * @param order_id
     * @param item_id
     */
    public static void delete(Connection connection, int order_id, int item_id) {
        try {
            String sql = "DELETE FROM ItemOrder WHERE order_id = ? AND item_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_id);
            statement.setInt(2, item_id);

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
     * delete all items in an order
     * @param connection
     * @param order_id
     */
    public static void delete(Connection connection, int order_id) {
        try {
            String sql = "DELETE FROM ItemOrder WHERE order_id = ?";
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

    static public ResultSet getAllItemsInOrder(Connection connection, int order_id) {

        String sql = "SELECT item_id, quantity FROM ItemOrder WHERE order_id = ? ";

        try {
            PreparedStatement pstmnt = connection.prepareStatement(sql);
            pstmnt.setInt(1, order_id);
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