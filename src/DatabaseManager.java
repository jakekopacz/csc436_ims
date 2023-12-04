//Facade Class

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {


    // Item Add, Update, Delete
    private void addItem() {

    }
    private void updateItem() {

    }
    private void deleteItem() {

    }

    private void addCustomer() {

    }

    private void addSupplier() {

    }

    // Sales Order ADD, Update, fulfill, cancel
    private void addSalesOrder() {

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
    private void updateSalesOrder() {

    }
    public static void removeSalesOrder(Connection conn, int salesOrder_id) {
        ItemOrderDb.delete(conn, salesOrder_id);
        SalesOrderDb.delete(conn, salesOrder_id);
    }

    // Replenish Order ADD, Update, received, cancel
    private void addReplenishOrder() {

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
