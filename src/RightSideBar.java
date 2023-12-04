import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.SpringLayout;
// Features that are specific to each table
public class RightSideBar {

    RightSideBar(TableOptions.options op, View view) {

        this.panel = new JPanel();

        makeItemBar(view);


    }


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
        }
        this.panel.revalidate();
        this.panel.repaint();
        System.out.println("RightSideBar Called");

    }

    public void makeSideBar(TableOptions.options op, View view, int id) {

        this.panel.removeAll();

        switch (op) {
            case ITEMIZED_ORDER:
                makeSalesOrderItemizedBar(view, id);
                break;
            case ITEMIZED_DELIVERY:
                break;
            case ITEM_SUPPLIER:
                makeItemSupplierBar(view, id);
                break;
        }
        this.panel.revalidate();
        this.panel.repaint();
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

        this.seeSuppliers = new JButton("Suppliers");


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
        this.panel.add(seeSuppliers);
        this.panel.add(new JLabel("WW"));

        SpringUtilities.makeCompactGrid(panel,
                8, 2, //rows, cols
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

        addItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JTextField item_id = new JTextField(10);
                JTextField quantity = new JTextField(10);
                JTextField price = new JTextField(10);
                JTextField category = new JTextField(10);


                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("ID:"));
                myPanel.add(item_id);
                myPanel.add(Box.createVerticalStrut(10));
                myPanel.add(new JLabel("Quantity:"));
                myPanel.add(quantity);
                myPanel.add(Box.createVerticalStrut(10));
                myPanel.add(new JLabel("Price:"));
                myPanel.add(price);
                myPanel.add(new JLabel("Description:"));
                myPanel.add(category);



                int result = JOptionPane.showConfirmDialog(view, myPanel,
                        null, JOptionPane.OK_CANCEL_OPTION);



                if (result == JOptionPane.OK_OPTION) {
                    int id = Integer.parseInt(item_id.getText());
                    int quant = Integer.parseInt(quantity.getText());
                    double prc = Double.parseDouble(price.getText());
                    String cat = category.getText();
                    ItemDb.insert(view.conn, id, quant, prc, cat);
                    view.refreshScrollPane(ItemDb.getAll(view.conn), TableOptions.options.ITEM);
                }

            }
        });

        seeSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }
                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                view.refreshScrollPane(SupplierListDb.getAll(view.conn, id), TableOptions.options.ITEM_SUPPLIER, id);

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


        // Done
        seeDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }
                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                view.refreshScrollPane(SalesOrderDb.getAllItemized(view.conn, id), TableOptions.options.ITEMIZED_ORDER, id);

            }
        });

        // todo
        addOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JTextField order_id = new JTextField(5);
                JTextField shipping_option = new JTextField(5);
                JTextField cemail = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Order ID:"));
                myPanel.add(order_id);
                myPanel.add(Box.createHorizontalStrut(10));
                myPanel.add(new JLabel("Shipping Option:"));
                myPanel.add(shipping_option);
                myPanel.add(Box.createHorizontalStrut(10)); // a spacer
                myPanel.add(new JLabel("Customer Email:"));
                myPanel.add(cemail);

                int result = JOptionPane.showConfirmDialog(view, myPanel,
                        null, JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int o_id = Integer.parseInt(order_id.getText());

                    SalesOrderDb.insert(view.conn, o_id, shipping_option.getText(), "123Ship", cemail.getText());
                    view.refreshScrollPane(SalesOrderDb.getAllItemized(view.conn, o_id), TableOptions.options.ITEMIZED_ORDER, o_id);
                }

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }
                int id = Integer.parseInt(view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString());

                view.refreshScrollPane(SalesOrderDb.getAllItemized(view.conn, id), TableOptions.options.ITEMIZED_ORDER, id);


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

    private void makeSalesOrderItemizedBar(View view, int order_id) {

        this.back = new JButton("Back");
        back.setMaximumSize(new Dimension(80, 40));
        this.addOrderItem = new JButton("Add Item");
        addOrderItem.setMaximumSize(new Dimension(80, 40));
        this.removeOrderItem = new JButton("Remove Item");
        removeOrderItem.setMaximumSize(new Dimension(80, 40));

        //DONE
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(SalesOrderDb.getAll(view.conn), TableOptions.options.SALES_ORDER);
            }
        });

        addOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JTextField item_id = new JTextField(5);
                JTextField quantity = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Item ID:"));
                myPanel.add(item_id);
                myPanel.add(Box.createHorizontalStrut(10));
                myPanel.add(new JLabel("Quantity:"));
                myPanel.add(quantity);

                int result = JOptionPane.showConfirmDialog(view, myPanel,
                        null, JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    ItemOrderDb.insert(view.conn, order_id, Integer.parseInt(item_id.getText()), Integer.parseInt(quantity.getText()));
                    view.refreshScrollPane(SalesOrderDb.getAllItemized(view.conn, order_id), TableOptions.options.ITEMIZED_ORDER, order_id);
                }
            }
        });



        SpringLayout layout = new SpringLayout();
        this.panel.setLayout(layout);
        this.panel.add(this.back);
        this.panel.add(this.addOrderItem);
        this.panel.add(this.removeOrderItem);

        SpringUtilities.makeCompactGrid(panel,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad


    }

    private void makeItemSupplierBar(View view, int item_id) {
        System.out.println("YES");
        this.addSupplier = new JButton("Add Supplier");
        this.removeSupplier = new JButton("Remove Supplier");
        JButton backButton = new JButton("Back");

        SpringLayout layout = new SpringLayout();
        this.panel.setLayout(layout);
        this.panel.add(backButton);
        this.panel.add(addSupplier);
        this.panel.add(removeSupplier);

        SpringUtilities.makeCompactGrid(panel,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.refreshScrollPane(ItemDb.getAll(view.conn), TableOptions.options.ITEM);
            }
        });

        removeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (view.jTable.getSelectedRow() == -1) {
                    return;
                }

                String id = view.jTable.getValueAt(view.jTable.getSelectedRow(),0).toString();

                int choice = JOptionPane.showConfirmDialog(
                        view, "Delete Supplier " + id,"Confirm",JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    SupplierListDb.removeSupplier(view.conn, id);
                    view.refreshScrollPane(SupplierListDb.getAll(view.conn, item_id), TableOptions.options.ITEM_SUPPLIER,item_id);
                }

            }
        });

        addSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JComboBox<String> suppliers = new JComboBox<String>();
                AutoCompletion.enable(suppliers);
                ResultSet rs = SupplierDb.getAll(view.conn);

                try {
                    while (rs.next()) {
                        suppliers.addItem(rs.getString("email"));
                        System.out.println(rs.getString("email"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                JTextField cost = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Supplier:"));
                myPanel.add(suppliers);
                myPanel.add(new JLabel("Cost:"));
                myPanel.add(cost);


                int result = JOptionPane.showConfirmDialog(view, myPanel,
                        null, JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String sup = suppliers.getSelectedItem().toString();
                    Double c = Double.parseDouble(cost.getText());

                    SupplierListDb.insert(view.conn, c, sup, item_id);
                    view.refreshScrollPane(SupplierListDb.getAll(view.conn, item_id), TableOptions.options.ITEM_SUPPLIER, item_id);
                }

            }
        });

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
    private javax.swing.JButton seeSuppliers;

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

    // Item Supplier
    javax.swing.JButton addSupplier;
    javax.swing.JButton removeSupplier;

}

//
//    JComboBox<String> supplier = new JComboBox<String>();
//
//    ResultSet rs = SupplierDb.getAll(view.conn);
//
//                while (true) {
//                        try {
//                        if (!rs.next()) {
//                        supplier.addItem(rs.getString("email"));
//                        }
//                        break;
//                        } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                        }
//
//                        }