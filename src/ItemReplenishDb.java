
import java.sql.*;

public class ItemReplenishDb {

    public static void insert(Connection connection, int replenish_id, int item_id, int quantity) {
        try {
            String sql = "INSERT INTO ItemReplenish (replenish_id, item_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, replenish_id);
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
     * @param replenish_id
     * @param item_id
     */
    public static void delete(Connection connection, int replenish_id, int item_id) {
        try {
            String sql = "DELETE FROM ItemReplenish WHERE replenish_id = ? AND item_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, replenish_id);
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
     * @param replenish_id
     */
    public static void delete(Connection connection, int replenish_id) {

        try {
            String sql = "DELETE FROM ItemReplenish WHERE replenish_id = ?";
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

    public static void deleteItemFromAllOrders(Connection connection, int item_id) {
        try {
            String sql = "DELETE FROM ItemReplenish WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, item_id);

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
    static public ResultSet getAllItemsInReplenish(Connection connection, int replenish_id) {

        String sql = "SELECT item_id, quantity FROM ItemReplenish WHERE replenish_id = ? ";

        try {
            PreparedStatement pstmnt = connection.prepareStatement(sql);
            pstmnt.setInt(1, replenish_id);
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