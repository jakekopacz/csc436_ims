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
    static public javax.swing.JTable makeJTable(ResultSet rs, Connection conn) {

        javax.swing.JTable jTable;
        jTable = new JTable(DbUtils.resultSetToTableModel(rs))  {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column < 1) {
                    return false;
                } else {
                    return true;
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
                if (e.getColumn() != 1) {
                    return;
                }
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();
                String columnName = model.getColumnName(column);

                Object item_id = model.getValueAt(row, 0);
                Object data = model.getValueAt(row, column);


                System.out.println(item_id);
                System.out.println(data);

                int id = Integer.parseInt(item_id.toString());


                System.out.println(item_id);
                System.out.println(data);

                ReplenishOrderDb.update(conn, id, "shipping_option", data.toString());

            }
        } );

        return jTable;
    }

    public void itemTableChangedQuantity(TableModelEvent e) {

        if (e.getColumn() != 1) {
            return;
        }
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

        System.out.println(columnName);
        System.out.println(data);

    }

}
