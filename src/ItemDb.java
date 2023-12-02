
import java.sql.*;

public class ItemDb {

    public static void insert(Connection connection, int item_id, int quantity, float price, String category) {
        try {
            String sql = "INSERT INTO item (item_id, quantity, price, category) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, item_id);
            statement.setInt(2, quantity);
            statement.setFloat(3, price);
            statement.setString(4, category);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * delete by item_id
     * @param connection
     * @param item_id
     */
    public static void delete(Connection connection, int item_id) {
        try {
            String sql = "DELETE FROM item WHERE item_id = ?";
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


    /**
     * update quantity by item_id
     * @param connection
     * @param item_id
     * @param quantity
     */
    public static void update(Connection connection, int item_id, int quantity) {
        try {
            String sql = "UPDATE item SET quantity = ? WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, item_id);

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

    /**
     * update price by item_id
     * @param connection
     * @param item_id
     * @param price
     */
    public static void update(Connection connection, int item_id, float price) {
        try {
            String sql = "UPDATE item SET price = ? WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1, price);
            statement.setInt(2, item_id);

            int rowsAffected = statement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Item modified successfully.");
            } else {
                System.out.println("Item not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    static public ResultSet quantitySearch(int min, int max, Connection connection) {
//
//
//        String sql = "SELECT * FROM item WHERE quantity >= ? and quantity <= ?;";
//        try {
//            PreparedStatement pstmnt = connection.prepareStatement(sql);
//            pstmnt.setInt(1, min);
//            pstmnt.setInt(2, max);
//            ResultSet rs = pstmnt.executeQuery();
//
//            rs.close();
//            pstmnt.close();
//            System.out.println("Search GOOD");
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//
//        return results;
//
//    }

}
