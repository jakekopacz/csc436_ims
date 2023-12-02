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


        switch (op) {

            case ITEM:
                return makeItem(jTable, conn);

        }

        return null;
    }
    static private javax.swing.JTable makeItem(JTable jTable, Connection conn) {

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
