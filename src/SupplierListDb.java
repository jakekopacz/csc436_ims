

import java.sql.*;

public class SupplierListDb {


    public static void removeItem(Connection conn, int item_id) {

        //removeSupplier(conn, item_id);

        try {
            String sql = "DELETE FROM SupplierList WHERE item_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, item_id);

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

//    public static void removeSupplier(Connection connection, int item_id) {
//
//        try {
//            String sql = "UPDATE SupplierList SET supplier_email = NULL WHERE item_id = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, item_id);
//
//            int rowsAffected = statement.executeUpdate();
//            connection.commit();
//            if (rowsAffected > 0) {
//                System.out.println("Item removed successfully.");
//            } else {
//                System.out.println("Item not found.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
