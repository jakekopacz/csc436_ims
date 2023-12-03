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

        // just an example this needs to change based on table
        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(4);
        topPanel.add(petList);

        this.addButton = new JButton();
        topPanel.add(addButton);
        this.addButton.setText("Search");



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
