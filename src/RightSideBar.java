import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.SpringLayout;
// Features that are specific to each table
public class RightSideBar {
    public enum options { // specifies each table
        ITEM,
        CUSTOMER,
        SUPPLIER,
        SALES_ORDER,
        REPLENISH_ORDER,
        ITEM_ORDER,
        ITEM_REPLENISH,
        SUPPLIER_LIST
    }

    RightSideBar(options op) {

        switch (op) {

            case ITEM:
                makeItemBar();
        }

    }
    RightSideBar() {
        makeItemBar();
    }
    private void makeItemBar() {

        this.quantity = new JLabel("Quantity:");
        this.minQuantity = new JLabel("Min:");
        this.maxQuantity = new JLabel("Max:");
        this.minQuantityInput = new JTextField(5);
        minQuantityInput.setMaximumSize(new Dimension(80, 30));
        this.maxQuantityInput = new JTextField(5);
        maxQuantityInput.setMaximumSize(new Dimension(80, 30));


        this.price = new JLabel("Price:");
        this.minPrice = new JLabel("Min:");
        this.maxPrice = new JLabel("Max:");
        this.minPriceInput = new JTextField(5);
        minPriceInput.setMaximumSize(new Dimension(80, 30));
        this.maxPriceInput = new JTextField(5);
        maxPriceInput.setMaximumSize(new Dimension(80, 30));

        this.quantityFilterButton = new JButton("Filter");
        this.priceFilterButton = new JButton("Filter");




        SpringLayout layout = new SpringLayout();
        this.panel = new JPanel(layout);
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



    }

    public JPanel getRightSideBar() {
        return this.panel;


    }

    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;

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


}


