import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.SpringLayout;
// Features that are specific to each table
public class LeftSideBar {

    LeftSideBar(View view) {
        makeSideBar(view);
    }
    private void makeSideBar(View view) {

        this.panel = new JPanel();
        this.buttonInventory = new javax.swing.JButton("Inventory");
        this.buttonOrders = new javax.swing.JButton("Orders");
        this.buttonDeliveries = new javax.swing.JButton("Deliveries");
        this.buttonCustomers = new javax.swing.JButton("Customers");
        this.buttonSuppliers = new javax.swing.JButton("Suppliers");



        SpringLayout layout = new SpringLayout();
        this.panel = new JPanel(layout);
        this.panel.add(buttonInventory);
        this.panel.add(buttonOrders);
        this.panel.add(buttonDeliveries);
        this.panel.add(buttonCustomers);
        this.panel.add(buttonSuppliers);

        SpringUtilities.makeCompactGrid(panel,
                5, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad


        // need to add check that input is valid number (i.e no chars)
        buttonInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(ItemDb.getAll(view.conn), TableOptions.options.ITEM);
            }
        });

        buttonOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(SalesOrderDb.getAll(view.conn), TableOptions.options.SALES_ORDER);
            }
        });

        buttonDeliveries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(ReplenishOrderDb.getAll(view.conn), TableOptions.options.REPLENISH_ORDER);
            }
        });

        buttonCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(CustomerDb.getAll(view.conn), TableOptions.options.CUSTOMER);
            }
        });

        buttonSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(SupplierDb.getAllCols(view.conn), TableOptions.options.SUPPLIER);
            }
        });

    }

    public JPanel getLeftSideBar() {
        return this.panel;

    }

    private javax.swing.JPanel panel;
    private javax.swing.JButton buttonInventory;
    private javax.swing.JButton buttonOrders;
    private javax.swing.JButton buttonDeliveries;
    private javax.swing.JButton buttonCustomers;
    private javax.swing.JButton buttonSuppliers;
    private javax.swing.JButton buttonAdd;


}


