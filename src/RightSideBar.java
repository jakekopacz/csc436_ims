import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.SpringLayout;
// Features that are specific to each table
public class RightSideBar {

    RightSideBar(TableOptions.options op, View view) {

        this.panel = new JPanel();

        switch (op) {
            case ITEM:
                makeItemBar(view);
                break;
            case SALES_ORDER:
                makeSalesOrderBar(view);
                break;
            case REPLENISH_ORDER:
                makeReplenishOrderBar(view);
                break;
        }

    }

    // TO - DO Work on layout of components
    public void makeSideBar(TableOptions.options op, View view) {

        this.panel.removeAll();

        switch (op) {
            case ITEM:
                makeItemBar(view);
                break;
            case SALES_ORDER:
                makeSalesOrderBar(view);
                break;
            case REPLENISH_ORDER:
                makeReplenishOrderBar(view);
                break;
            case CUSTOMER:
                break;
            case SUPPLIER:
                break;
            case ITEMIZED_ORDER:
                break;
            case ITEMIZED_DELIVERY:
                break;
        }
        this.panel.revalidate();
        this.panel.repaint();
        System.out.println("RightSideBar Called");

    }

    private void makeItemBar(View view) {


        this.quantity = new JLabel("Quantity");
        this.minQuantity = new JLabel("Min:");
        this.maxQuantity = new JLabel("Max:");
        this.minQuantityInput = new JTextField(5);
        minQuantityInput.setMaximumSize(new Dimension(80, 30));
        this.maxQuantityInput = new JTextField(5);
        maxQuantityInput.setMaximumSize(new Dimension(80, 30));


        this.price = new JLabel("Price");
        this.minPrice = new JLabel("Min:");
        this.maxPrice = new JLabel("Max:");
        this.minPriceInput = new JTextField(5);
        minPriceInput.setMaximumSize(new Dimension(80, 30));
        this.maxPriceInput = new JTextField(5);
        maxPriceInput.setMaximumSize(new Dimension(80, 30));

        this.quantityFilterButton = new JButton("Filter");
        this.priceFilterButton = new JButton("Filter");
        this.addItem = new JButton("Add Item");
        this.removeItem = new JButton("Remove Item");


        SpringLayout layout = new SpringLayout();
        this.panel.setLayout(layout);
        this.panel.add(quantity);
        this.panel.add(price);
        this.panel.add(minQuantity);
        this.panel.add(minPrice);
        this.panel.add(minQuantityInput);
        this.panel.add(minPriceInput);
        this.panel.add(maxQuantity);
        this.panel.add(maxPrice);
        this.panel.add(maxQuantityInput);
        this.panel.add(maxPriceInput);
        this.panel.add(quantityFilterButton);
        this.panel.add(priceFilterButton);
        this.panel.add(addItem);
        this.panel.add(removeItem);

        SpringUtilities.makeCompactGrid(panel,
                7, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad


        // need to add check that input is valid number (i.e no chars)
        quantityFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int min = 0;
                int max = Integer.MAX_VALUE;
                if (!minQuantityInput.getText().isEmpty()) {
                    min = Integer.parseInt(minQuantityInput.getText());
                }
                if (!maxQuantityInput.getText().isEmpty()){
                    max = Integer.parseInt(maxQuantityInput.getText());
                }

                if (min != 0 || max != Integer.MAX_VALUE) {
                    view.refreshScrollPane(ItemDb.quantitySearch(min, max, view.conn), TableOptions.options.ITEM);
                }
                else {
                    view.refreshScrollPane(ItemDb.getAll(view.conn), TableOptions.options.ITEM);
                }
                System.out.println(min + " , " + max);
            }
        });

        priceFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int min = 0;
                int max = Integer.MAX_VALUE;
                if (!minPriceInput.getText().isEmpty()) {
                    min = Integer.parseInt(minPriceInput.getText());
                }
                if (!maxPriceInput.getText().isEmpty()){
                    max = Integer.parseInt(maxPriceInput.getText());
                }

                if (min != 0 || max != Integer.MAX_VALUE) {
                    view.refreshScrollPane(ItemDb.priceSearch(min, max, view.conn), TableOptions.options.ITEM);
                }
                else {
                    view.refreshScrollPane(ItemDb.getAll(view.conn), TableOptions.options.ITEM);
                }
                System.out.println(min + " , " + max);
            }
        });

        removeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }

                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                int choice = JOptionPane.showConfirmDialog(
                        view, "Warning: Deleting item " + id + " will remove it from all orders/deliveries", "Confirm",JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    DatabaseManager.deleteItem(view.conn, id);
                    view.refreshScrollPane(ItemDb.getAll(view.conn), TableOptions.options.ITEM);
                }
            }
        });


    }

    private void makeSalesOrderBar(View view) {


        this.seeDetails = new JButton("Itemized Details");
        seeDetails.setMaximumSize(new Dimension(80, 40));
        this.addOrder = new JButton("Add Order");
        addOrder.setMaximumSize(new Dimension(80, 40));
        this.removeOrder = new JButton("Remove Order");
        removeOrder.setMaximumSize(new Dimension(80, 40));
        this.fulfillOrder = new JButton("Order Fulfilled");
        fulfillOrder.setMaximumSize(new Dimension(80, 40));


        // todo
        seeDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }
                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                view.refreshScrollPane(SalesOrderDb.getAllItemized(view.conn, id), TableOptions.options.ITEMIZED_ORDER);

            }
        });

        // todo
        addOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });
        //done
        removeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }

                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                int choice = JOptionPane.showConfirmDialog(
                        view, "Delete Order " + id,"Confirm",JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    DatabaseManager.removeSalesOrder(view.conn, id);
                    view.refreshScrollPane(SalesOrderDb.getAll(view.conn), TableOptions.options.SALES_ORDER);
                }
            }
        });

        //done
        fulfillOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }

                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                int choice = JOptionPane.showConfirmDialog(
                        view, "Order " + id + " Sent?","Confirm",JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    DatabaseManager.fulfillSalesOrder(view.conn, id);
                    view.refreshScrollPane(SalesOrderDb.getAll(view.conn), TableOptions.options.SALES_ORDER);

                }
            }
        });

        SpringLayout layout = new SpringLayout();
        this.panel.setLayout(layout);
        this.panel.add(this.seeDetails);
        this.panel.add(this.addOrder);
        this.panel.add(this.removeOrder);
        this.panel.add(this.fulfillOrder);




        SpringUtilities.makeCompactGrid(panel,
                4, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

    }

    private void makeReplenishOrderBar(View view) {


        this.seeDetailsRep = new JButton("Itemized Details");
        seeDetailsRep.setMaximumSize(new Dimension(80, 40));
        this.addDelivery = new JButton("Add Delivery");
        addDelivery.setMaximumSize(new Dimension(80, 40));
        this.removeDelivery = new JButton("Remove Delivery");
        removeDelivery.setMaximumSize(new Dimension(80, 40));
        this.fulfillDelivery = new JButton("Delivery Received");
        fulfillDelivery.setMaximumSize(new Dimension(80, 40));

        // todo
        seeDetailsRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                // need a popup window of a Jscroll Pane
                // call upon view.jtable.getSelected();
                // calls order_item_quantity_price where order_id = selected order_id
                // pop up window needs to allow add / remove of items
            }
        });

        // todo
        addDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                // need a popup window of a Jscroll Pane
                // call upon view.jtable.getSelected();
                // calls order_item_quantity_price where order_id = selected order_id
                // pop up window needs to allow add / remove of items
            }
        });

        // done
        removeDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }

                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                int choice = JOptionPane.showConfirmDialog(
                        view, "Delete Delivery " + id,"Confirm",JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    DatabaseManager.removeReplenishOrder(view.conn, id);
                    view.refreshScrollPane(ReplenishOrderDb.getAll(view.conn), TableOptions.options.REPLENISH_ORDER);
                }

                System.out.println(view.jTable.getValueAt(view.jTable.getSelectedRow(),0));

            }
        });

        // done
        fulfillDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }

                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                int choice = JOptionPane.showConfirmDialog(
                        view, "Delivery " + id + " Received?","Confirm",JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    DatabaseManager.fulfillReplenishOrder(view.conn, id);
                    view.refreshScrollPane(ReplenishOrderDb.getAll(view.conn), TableOptions.options.REPLENISH_ORDER);

                }

            }
        });

        SpringLayout layout = new SpringLayout();
        this.panel.setLayout(layout);
        this.panel.add(this.seeDetailsRep);
        this.panel.add(this.addDelivery);
        this.panel.add(this.removeDelivery);
        this.panel.add(this.fulfillDelivery);




        SpringUtilities.makeCompactGrid(panel,
                4, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

    }

    private void makeSalesOrderItemizedBar(View view) {

        this.back = new JButton("Back");
        back.setMaximumSize(new Dimension(80, 40));
        this.addOrderItem = new JButton("Add Delivery");
        addDelivery.setMaximumSize(new Dimension(80, 40));
        this.removeOrderItem = new JButton("Remove Delivery");
        removeDelivery.setMaximumSize(new Dimension(80, 40));

    }

    public JPanel getRightSideBar() {
        return this.panel;

    }

    public javax.swing.JPanel panel;

    // ITEM COMPONENTS
    private javax.swing.JButton quantityFilterButton;
    private javax.swing.JLabel quantity;
    private javax.swing.JLabel minQuantity;
    private javax.swing.JLabel maxQuantity;
    private javax.swing.JTextField minQuantityInput;
    private javax.swing.JTextField maxQuantityInput;

    private javax.swing.JButton priceFilterButton;
    private javax.swing.JLabel price;
    private javax.swing.JLabel minPrice;
    private javax.swing.JLabel maxPrice;
    private javax.swing.JTextField minPriceInput;
    private javax.swing.JTextField maxPriceInput;

    private javax.swing.JButton addItem;
    private javax.swing.JButton removeItem;

    // SALES_ORDER COMPONENTS
    private javax.swing.JButton fulfillOrder;
    private javax.swing.JButton seeDetails;
    private javax.swing.JButton addOrder;
    private javax.swing.JButton removeOrder;

    // REPLENISH_ORDER COMPONENTS
    private javax.swing.JButton fulfillDelivery;
    private javax.swing.JButton seeDetailsRep;
    private javax.swing.JButton addDelivery;
    private javax.swing.JButton removeDelivery;


    // Itemized Order Components
    private javax.swing.JButton back;
    private javax.swing.JButton addOrderItem;
    private javax.swing.JButton removeOrderItem;

}


