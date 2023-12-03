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
        }

    }

    public void makeSideBar(TableOptions.options op, View view) {

        this.panel.removeAll();

        switch (op) {
            case ITEM:
                makeItemBar(view);
                break;
            case SALES_ORDER:
                System.out.println("SalesOrderBar");
                makeSalesOrderBar(view);
                break;
        }
        this.panel.revalidate();
        this.panel.repaint();
        System.out.println("SalesOrderBarLL");

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

        SpringUtilities.makeCompactGrid(panel,
                6, 2, //rows, cols
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

    }

    private void makeSalesOrderBar(View view) {


        this.seeDetails = new JButton("Order Details");
        seeDetails.setMaximumSize(new Dimension(80, 40));

        priceFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                // need a popup window of a Jscroll Pane
                // call upon view.jtable.getSelected();
                // calls order_item_quantity_price where order_id = selected order_id
                // pop up window needs to allow add / remove of items
            }
        });

        SpringLayout layout = new SpringLayout();
        this.panel.setLayout(layout);
        this.panel.add(this.seeDetails);



        SpringUtilities.makeCompactGrid(panel,
                1, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

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

    // SALES_ORDER COMPONENTS
    private javax.swing.JButton seeDetails;
//    private javax.swing.JLabel quantity;
//    private javax.swing.JLabel minQuantity;
//    private javax.swing.JLabel maxQuantity;
//    private javax.swing.JTextField minQuantityInput;
//    private javax.swing.JTextField maxQuantityInput;
//
//    private javax.swing.JButton priceFilterButton;
//    private javax.swing.JLabel price;
//    private javax.swing.JLabel minPrice;
//    private javax.swing.JLabel maxPrice;
//    private javax.swing.JTextField minPriceInput;
//    private javax.swing.JTextField maxPriceInput;


}


