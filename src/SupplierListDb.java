

import java.sql.*;

public class SupplierListDb {


    public static void insert(Connection connection, double cost, String supplier_email, int item_id) {
        try {
            String sql = "INSERT INTO SupplierList (cost, supplier_email, item_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, cost);
            statement.setString(2, supplier_email);
            statement.setInt(3, item_id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static public ResultSet getAll(Connection connection, int item_id) {

        String sql = "SELECT supplier_email, cost FROM SupplierList WHERE item_id = ?";

        try {
            PreparedStatement pstmnt = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            pstmnt.setInt(1,item_id);
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

    public static void removeSupplier(Connection conn, String email) {

        //removeSupplier(conn, item_id);

        try {
            String sql = "DELETE FROM SupplierList WHERE supplier_email = ?";
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
