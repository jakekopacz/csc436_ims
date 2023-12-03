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
                if (op == TableOptions.options.SALES_ORDER || op == TableOptions.options.REPLENISH_ORDER) {
                    if (column < 2) {
                        return false;
                    } else {
                        return true;
                    }
                }
                else {
                    if (column < 1) {
                        return false;
                    } else {
                        return true;
                    }
                }

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

                int id = Integer.parseInt(row_id.toString());
                String columnName = model.getColumnName(column);

                System.out.println(id);
                System.out.println(data);

                switch (op) {

                    case ITEM:
                        makeItem(conn, columnName, id, data);
                        break;
                    case SALES_ORDER:
                        makeSalesOrder(conn, columnName, id, data);
                        System.out.println("makeSalesOrder");
                        break;
                    case REPLENISH_ORDER:
                        makeReplenishOrder(conn, columnName, id, data);

                }

            }
        } );
        
        return jTable;
    }

    static private void makeItem(Connection conn, String columnName, int id, Object data) {

        switch (columnName) {
            case "quantity" -> ItemDb.update(conn, id, Integer.parseInt(data.toString()));
            case "price" -> ItemDb.update(conn, id, Double.parseDouble(data.toString()));
            case "category" -> ItemDb.update(conn, id, data.toString());
        }
    }


    static private void makeSalesOrder(Connection conn, String columnName, int id, Object data) {

        SalesOrderDb.update(conn, id, columnName, data.toString());

    }

    static private void makeReplenishOrder(Connection conn, String columnName, int id, Object data) {

        ReplenishOrderDb.update(conn, id, columnName, data.toString());

    }

}
