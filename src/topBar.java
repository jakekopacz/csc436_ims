import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

/*Class for topBar */
public class topBar {

    private View mainView;

    topBar(View view) {
        this.mainView = view;
        this.topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        this.searchInput = new JTextField(30);
        this.searchOptions = new JComboBox<String>();
        this.searchButton = new JButton();
        this.searchButton.setText("Search");

        this.topPanel.add(this.searchInput);
        this.topPanel.add(this.searchOptions);
        this.topPanel.add(searchButton);
    }

    public void makeTopBar() {
        
        this.searchOptions.removeAllItems();
        
        for(int i = 0, sz = this.mainView.jTable.getColumnCount(); i < sz; i++){
            this.searchOptions.addItem(this.mainView.jTable.getColumnName(i));
        }

    }

    public JPanel getTopBar() {
        return this.topPanel;
    }

    private javax.swing.JPanel topPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchInput;
    private javax.swing.JComboBox<String> searchOptions;

}
