//Facade Class
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {


    // Deleting an Item will result in it being removed from SupplierList,
    // and all orders/replenishments that contain that ite
    public static void deleteItem(Connection conn, int item_id) {

        SupplierListDb.removeItem(conn, item_id);
        ItemOrderDb.deleteItemFromAllOrders(conn, item_id);
        ItemReplenishDb.deleteItemFromAllOrders(conn, item_id);
        ItemDb.delete(conn, item_id);
    }

    public static void removeCustomer(Connection conn, String email) throws SQLException {

        ResultSet rs = SalesOrderDb.getCustomer(conn, email);

        if (rs.next() == false) {
            CustomerDb.deleteMailingList(conn, email);
            CustomerDb.delete(conn, email);
        }
        else {
            System.out.println("Customer currently has an order");
        }

    }

    private void addSupplier() {

    }

    public static void deleteSupplier(Connection conn, String email) {
        SupplierListDb.removeSupplier(conn, email);
        SupplierDb.removeSupplier(conn, email);

    }

    // Add order to SalesOrder
    // Add order's items to ItemOrder
    public static void addSalesOrder(Connection conn, int order_id, String shipping_option, String tracking_num, String customer_email, ArrayList<Integer> item_ids, ArrayList<Integer> item_quantities) {

        if (item_ids.size() != item_quantities.size()) {return;}

        SalesOrderDb.insert(conn, order_id, shipping_option, tracking_num, customer_email);

        for (int i = 0; i < item_ids.size(); i++) {
            ItemOrderDb.insert(conn, order_id, item_ids.get(i), item_quantities.get(i));
        }

    }


    public static void fulfillSalesOrder(Connection conn, int order_id) {

        // get items and quantities,
        ResultSet rs = ItemOrderDb.getAllItemsInOrder(conn, order_id);
        try {
            while (rs.next()) { // should be a separate function
                int id = rs.getInt("item_id");
                int quantity = rs.getInt("quantity");
                quantity *= -1;
                ItemDb.incriment(conn, id, quantity);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // add to items
        removeSalesOrder(conn, order_id);

    }

    public static void removeSalesOrder(Connection conn, int salesOrder_id) {
        ItemOrderDb.delete(conn, salesOrder_id);
        SalesOrderDb.delete(conn, salesOrder_id);
    }


    private void addReplenishOrder(Connection conn, int replenish_id, String shipping_option, String tracking_num, String supplier_email, ArrayList<Integer> item_ids, ArrayList<Integer> item_quantities) {

        if (item_ids.size() != item_quantities.size()) {return;}

        ReplenishOrderDb.insert(conn, replenish_id, shipping_option, tracking_num, supplier_email);

        for (int i = 0; i < item_ids.size(); i++) {
            ItemReplenishDb.insert(conn, replenish_id, item_ids.get(i), item_quantities.get(i));
        }
    }

    public static void fulfillReplenishOrder(Connection conn, int replenishOrder_id) {

        // get items and quantities,
        ResultSet rs = ItemReplenishDb.getAllItemsInReplenish(conn, replenishOrder_id);
        try {
            while (rs.next()) { // should be a separate function
                int id = rs.getInt("item_id");
                int quantity = rs.getInt("quantity");
                ItemDb.incriment(conn, id, quantity);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // add to items
        removeReplenishOrder(conn, replenishOrder_id);

    }
    public static void removeReplenishOrder(Connection conn, int replenishOrder_id) {

        ItemReplenishDb.delete(conn, replenishOrder_id);
        ReplenishOrderDb.delete(conn, replenishOrder_id);

    }

}
