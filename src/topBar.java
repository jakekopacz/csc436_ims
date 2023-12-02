import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

/*Class for topBar */
public class topBar {

    private View mainView;
    topBar(View view) {
        makeTopBar();
        this.mainView = view;
    }

    private void makeTopBar() {

        this.topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        this.searchInput = new JTextField(30);
        this.topPanel.add(this.searchInput);

        this.addButton = new JButton();
        topPanel.add(addButton);
        this.addButton.setText("Search");

        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainView.refreshScrollPane("SELECT * FROM item WHERE quantity >= 110 and quantity <= 900");
            }
        });


    }

    public JPanel getTopBar() {
        return this.topPanel;
    }

    private javax.swing.JPanel topPanel;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton addButton;
    private javax.swing.JLabel search;
    private javax.swing.JTextField searchInput;

}
