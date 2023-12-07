import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.sql.*;


public class JTableDb {

    /**
     * Call this when JTbale is needed
     * @param rs
     * @return JTable
     */

    static public javax.swing.JTable makeJTable(ResultSet rs, Connection conn, TableOptions.options op) {

        javax.swing.JTable jTable;
        jTable = new JTable(DbUtils.resultSetToTableModel(rs))  {
            @Override
            public boolean isCellEditable(int row, int column) {

                if (op == TableOptions.options.ITEM_SUPPLIER || op == TableOptions.options.ITEMIZED_DELIVERY || op == TableOptions.options.CUSTOMER_ORDERS) {
                    return false;
                }
                else if (op == TableOptions.options.SALES_ORDER || op == TableOptions.options.REPLENISH_ORDER) {
                    if (column < 2 || column == 4) {
                        return false;
                    }
                }
                else if (op == TableOptions.options.ITEMIZED_ORDER) {
                    if (column != 2) {
                        return false;
                    }
                }
                else {
                    if (column < 1) {
                        return false;
                    }
                }
                return true;

            }
        };

        jTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        jTable.getTableHeader().setResizingAllowed(false);
        jTable.setRowSelectionAllowed(true);
        jTable.setColumnSelectionAllowed(false);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();

                Object row_id = model.getValueAt(row, 0);
                Object data = model.getValueAt(row, column);

                String columnName = model.getColumnName(column);

                int id;
                String s_id;

                switch (op) {

                    case ITEM:
                        id = Integer.parseInt(row_id.toString());
                        makeItem(conn, columnName, id, data);
                        break;
                    case SALES_ORDER:
                        id = Integer.parseInt(row_id.toString());
                        makeSalesOrder(conn, columnName, id, data);
                        break;
                    case REPLENISH_ORDER:
                        id = Integer.parseInt(row_id.toString());
                        makeReplenishOrder(conn, columnName, id, data);
                    case CUSTOMER:
                        s_id =row_id.toString();
                        makeCustomer(conn, columnName, s_id, data.toString());
                        break;
                    case SUPPLIER:
                        s_id =row_id.toString();
                        break;
                    case ITEMIZED_ORDER:
                        int item_id = Integer.parseInt(model.getValueAt(row, 1).toString());
                        id = Integer.parseInt(row_id.toString());
                        makeItemizedOrder(conn, Integer.parseInt(data.toString()), id, item_id);



                }

            }
        } );
        
        return jTable;
    }

    static private void makeItem(Connection conn, String columnName, int id, Object data) {

        switch (columnName) {
		case "quantity": 
			ItemDb.update(conn, id, Integer.parseInt(data.toString()));
			break;
		case "price":
		       	ItemDb.update(conn, id, Double.parseDouble(data.toString()));
			break;
		case "category":
			ItemDb.update(conn, id, data.toString());
			break;
        }
    }


    static private void makeSalesOrder(Connection conn, String columnName, int id, Object data) {

        SalesOrderDb.update(conn, id, columnName, data.toString());

    }

    static private void makeReplenishOrder(Connection conn, String columnName, int id, Object data) {
        ReplenishOrderDb.update(conn, id, columnName, data.toString());
    }

    static private void makeItemizedOrder(Connection conn, int quantity,  int order_id, int item_id) {
        ItemOrderDb.update(conn, quantity, order_id, item_id);

    }

    static private void makeCustomer(Connection conn, String colName, String id, String data) {
        CustomerDb.update(conn, colName, id, data);
    }

    static private void makeSupplier() {

    }

}
