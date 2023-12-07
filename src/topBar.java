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
        this.searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                performSearch();
            }
        });
        this.topPanel.add(this.searchInput);
        this.topPanel.add(this.searchOptions);
        this.topPanel.add(searchButton);

    }

    private void performSearch(){
        javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter = new javax.swing.table.TableRowSorter<>(this.mainView.jTable.getModel());
        this.mainView.jTable.setRowSorter(sorter);
        String query = searchInput.getText();
        if(query.length() != 0){
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + query, this.searchOptions.getSelectedIndex()));
        } else {
            sorter.setRowFilter(null);
        }
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
    public JTextField getSearchInput(){
        return this.searchInput;
    }

    private javax.swing.JPanel topPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchInput;
    private javax.swing.JComboBox<String> searchOptions;

}