import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import net.proteanit.sql.DbUtils;

enum TableSet { INVENTORY_SET, ORDERS_SET, DELIVERIES_SET, CONTACTS_SET };
enum UpdateType { NULL, ADD, UPDATE, REMOVE };
enum UpdateEntity { NULL, ITEM, SALE, REPLENISH };


public class View extends javax.swing.JFrame {

    // wrapper for combobox in update panel; combobox calls tostring on everything
    public class ComboItem {
        private String key;
        private UpdateEntity value;
    
        public ComboItem(String key, UpdateEntity value) { this.key = key; this.value = value; }
    
        @Override
        public String toString() { return key; }
    
        public String getKey() { return key; }
        public UpdateEntity getValue() { return value; }
    }

    public View(Connection conn) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents(conn);
    }


    private void initComponents(Connection conn) {
	
	    this.conn = conn;
        // tabbed pane for viewing tables
        tablePane = new javax.swing.JTabbedPane();
        // scrollpanes for each tab
        itemScrollPane = new javax.swing.JScrollPane();
        salesScrollPane = new javax.swing.JScrollPane();
        replenishScrollPane = new javax.swing.JScrollPane();
        addressScrollPane = new javax.swing.JScrollPane();
        phoneScrollPane = new javax.swing.JScrollPane();
        // flags for update properties
        currentUpdateEntity = UpdateEntity.NULL;
        currentUpdateType = UpdateType.NULL;
        // set initial pane
        currentSet = TableSet.INVENTORY_SET;
        currentScrollPane = itemScrollPane;
        setTablePane();
        // set viewport of initial display (item table)
        refreshItemScrollPane(0);

        updatePanelVisible = false;
	// listener for changing tabs
        tablePane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
		int index = tablePane.getSelectedIndex();
		// getselectedindex returns -1 if nothing is selected; do nothing if changing table set
		if(index > -1){
			// clear current viewport
			currentScrollPane.setViewportView(new JTable());
			// change current scrollpane
			currentScrollPane = (JScrollPane)tablePane.getComponentAt(tablePane.getSelectedIndex());
			// refresh new scrollpane with jtable
                	switch(currentSet){
	                    case INVENTORY_SET:
        	            refreshItemScrollPane(tablePane.getSelectedIndex());
                	    break;
	                    case ORDERS_SET:
        	            refreshOrderScrollPane(tablePane.getSelectedIndex());
                	    break;
	                    case DELIVERIES_SET:
        	            refreshDeliveryScrollPane(tablePane.getSelectedIndex());
                	    break;
	                    case CONTACTS_SET:
        	            refreshContactScrollPane(tablePane.getSelectedIndex());
                	    break;
               		}
		}
            }
        });

        mainPanel = new javax.swing.JPanel();
        updatePanel = new javax.swing.JPanel();
        updateContainer = new javax.swing.JPanel();
        buttonInventory = new javax.swing.JButton();
        buttonOrders = new javax.swing.JButton();
        buttonDeliveries = new javax.swing.JButton();
        buttonContacts = new javax.swing.JButton();

        buttonAdd = new javax.swing.JButton();
        buttonUpdate = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();

	    buttonSubmit = new javax.swing.JButton();
	    buttonClose = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuEdit = new javax.swing.JMenu();
    
        updateMenu = new javax.swing.JComboBox<>();
        updateMenuTitle = new javax.swing.JLabel();
	    textfields = new ArrayList<JTextField>();
        labels = new ArrayList<JLabel>();

        updateMenu.setModel(new javax.swing.DefaultComboBoxModel<ComboItem>(new ComboItem[] {
             new ComboItem("", UpdateEntity.NULL), new ComboItem("Item", UpdateEntity.ITEM),
             new ComboItem("Sale", UpdateEntity.SALE), new ComboItem("Delivery", UpdateEntity.REPLENISH) 
        }));

        updateMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboItem item = (ComboItem)updateMenu.getSelectedItem();
                if(currentUpdateType != UpdateType.NULL){
                    removeUpdateContent();
                }
                currentUpdateEntity = item.getValue();
                postUpdateContent();
            }
        });
        updateMenuTitle.setText("Options");

        //refreshScrollPane("SELECT * FROM Item");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuFile.setText("File");
        menuBar.add(menuFile);

        menuEdit.setText("Edit");
        menuBar.add(menuEdit);

        setJMenuBar(menuBar);

        buttonInventory.setText("Inventory");
        buttonInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentSet = TableSet.INVENTORY_SET;
                setTablePane();
                refreshItemScrollPane(0);
            }
        });

        buttonOrders.setText("Orders");
        buttonOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentSet = TableSet.ORDERS_SET;
                setTablePane();
                refreshOrderScrollPane(0);
            }
        });

        buttonDeliveries.setText("Deliveries");
        buttonDeliveries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentSet = TableSet.DELIVERIES_SET;
                setTablePane();
                refreshDeliveryScrollPane(0);
            }
        });

        buttonContacts.setText("Contacts");
        buttonContacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentSet = TableSet.CONTACTS_SET;
                setTablePane();
                refreshContactScrollPane(0);
            }
        });

	buttonAdd.setText("Add");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(currentUpdateType != UpdateType.NULL){
                        removeUpdatePanel();
                    }
                    currentUpdateType = UpdateType.ADD;
        		    postUpdatePanel();
            }
    });

	buttonUpdate.setText("Update");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(currentUpdateType != UpdateType.NULL){
                        removeUpdatePanel();
                    }
                    currentUpdateType = UpdateType.UPDATE;
        		    postUpdatePanel();
            }
        });

	buttonRemove.setText("Remove");
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(currentUpdateType != UpdateType.NULL){
                        removeUpdatePanel();
                    }
                    currentUpdateType = UpdateType.REMOVE;
        		    postUpdatePanel();
            }
        });

	buttonSubmit.setText("Submit");
        buttonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
		ArrayList<String> input = new ArrayList<String>();
		for(JTextField text: textfields){
			input.add(text.getText());
		}
		for(String str: input){
			System.out.println(str);
		}
            }
        });

	buttonClose.setText("Close");
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
		        removeUpdatePanel();
                currentUpdateType = UpdateType.NULL;
            }
        });

	// set component layout; basically order everything in the x-axis, then in the y-axis. for things to be next to each other along an axis, use parallel group
	GroupLayout layout = new GroupLayout(mainPanel);
	mainPanel.setLayout(layout);
	layout.setAutoCreateGaps(true);
	layout.setAutoCreateContainerGaps(true);

	layout.setHorizontalGroup(
layout.createParallelGroup((GroupLayout.Alignment.LEADING))
   .addGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
           .addComponent(buttonInventory)
           .addComponent(buttonOrders)
           .addComponent(buttonDeliveries)
           .addComponent(buttonContacts))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
           .addComponent(tablePane)
           .addGroup(layout.createSequentialGroup()
                .addComponent(buttonAdd)
                .addComponent(buttonUpdate)
                .addComponent(buttonRemove)))
    )
   .addComponent(updatePanel));

   layout.setVerticalGroup(
   layout.createParallelGroup()
      .addGroup(layout.createSequentialGroup()
           .addComponent(buttonInventory)
           .addComponent(buttonOrders)
           .addComponent(buttonDeliveries)
           .addComponent(buttonContacts))
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup()
            .addComponent(buttonAdd)
            .addComponent(buttonUpdate)
            .addComponent(buttonRemove))
	   .addComponent(tablePane)
	   .addComponent(updatePanel))
      );


      add(mainPanel);

        pack();
	setSize(640, 480);
    }
private void setTablePane(){
    if(tablePane.getTabCount() > 0){
        tablePane.removeAll();
    }
    switch(this.currentSet){
        case INVENTORY_SET:
        tablePane.add("Inventory", itemScrollPane);
        break;
        case ORDERS_SET:
        tablePane.add("Incoming", salesScrollPane);
        tablePane.add("Outgoing", replenishScrollPane);
        break;
        case DELIVERIES_SET:
//        tablePane.add("Incoming", tableScrollPane);
//        tablePane.add("Outgoing", tableScrollPane);
        break;
        case CONTACTS_SET:
        tablePane.add("Addresses", addressScrollPane);
        tablePane.add("Phone Numbers", phoneScrollPane);
        break; 
    }
}

private void refreshItemScrollPane(int index){
    switch(index){
        case 0:
        refreshScrollPane("SELECT * FROM Item");
        break;
    }
}
private void refreshOrderScrollPane(int index){
    switch(index){
        case 0:
        refreshScrollPane("SELECT * FROM SalesOrder");
        break;
        case 1:
        refreshScrollPane("SELECT * FROM ReplenishOrder");
        break;
    }

}
private void refreshDeliveryScrollPane(int index){
    
}
private void refreshContactScrollPane(int index){
    switch(index){
        case 0:
        refreshScrollPane("SELECT * FROM MailingList");
        break;
        case 1:
        refreshScrollPane("SELECT * FROM PhoneList");
        break;
    }
    
}

    private void refreshScrollPane(String arg) {
        try{
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(arg);

        currentScrollPane.setViewportView(JTableDb.makeJTable(rs, this.conn));
        currentScrollPane.revalidate();
        currentScrollPane.repaint();
        } catch(SQLException ex){
            ex.printStackTrace();
        }

    }
    
/* post/remove update panel, and post/remove update content*/
    private void postUpdatePanel() {
        switch(currentUpdateType){
            case ADD:
            updatePanel.setBorder(BorderFactory.createTitledBorder("Add"));
            break;
            case UPDATE:
            updatePanel.setBorder(BorderFactory.createTitledBorder("Update"));
            break;
            case REMOVE:
            updatePanel.setBorder(BorderFactory.createTitledBorder("Remove"));
            break;
        }
        GroupLayout layout = new GroupLayout(updatePanel);
        updatePanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(updateMenuTitle)
                    .addComponent(updateMenu))
                .addComponent(updateContainer)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(buttonSubmit)
                    .addComponent(buttonClose))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(updateMenuTitle)
                    .addComponent(updateMenu))
                .addComponent(updateContainer)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(buttonSubmit)
                    .addComponent(buttonClose))
        );
        
        updatePanel.revalidate();
        updatePanel.repaint();
    }

    private void removeUpdatePanel() {
        if(currentUpdateEntity != UpdateEntity.NULL){
            removeUpdateContent();
        }
    	updatePanel.removeAll();
        updatePanel.setBorder(BorderFactory.createEmptyBorder());
    	updatePanel.revalidate();
    	updatePanel.repaint();
        updateMenu.setSelectedIndex(0);
        updatePanelVisible = false;
    }

    private void postUpdateContent(){
        switch(currentUpdateEntity){
            case NULL:
            return;

            case REPLENISH:
            labels.addAll(Arrays.asList(new JLabel("OrderID"), new JLabel("Shipping Option"), new JLabel("Tracking #"), new JLabel("Supplier Email")));
            break;
            case SALE:
            labels.addAll(Arrays.asList(new JLabel("OrderID"), new JLabel("Shipping Option"), new JLabel("Tracking #"), new JLabel("Customer Email")));
            break;
            case ITEM:
            labels.addAll(Arrays.asList(new JLabel("SKU"), new JLabel("Quantity"), new JLabel("Price"), new JLabel("Category")));
            break;
        }
        for(int i = 0; i < labels.size(); i++){
                textfields.add(new JTextField());
        }
        postUpdateForm();
    }
    // helper for displaying update form
    private void postUpdateForm(){
	    GroupLayout layout = new GroupLayout(updateContainer);
    	updateContainer.setLayout(layout);
    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup labelGroupH = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.ParallelGroup textGroupH = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup combinedGroupV = layout.createSequentialGroup();
        for(JLabel label: labels){
            labelGroupH.addComponent(label);
        }
        for(JTextField text: textfields){
            textGroupH.addComponent(text);
        }
        // add in each label/textfield pair
        for(int i = 0; i < textfields.size(); i++){
        //    textGroupH.addGroup(layout.createSequentialGroup().addComponent(labels.get(i), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(textfields.get(i)));
            combinedGroupV.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(labels.get(i)).addComponent(textfields.get(i)));
        }
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(labelGroupH).addGroup(textGroupH));
        layout.setVerticalGroup(combinedGroupV);
    }

    private void removeUpdateContent(){
        updateContainer.removeAll();
    	textfields.clear();
        labels.clear();
        currentUpdateEntity = UpdateEntity.NULL;
    }



    private javax.swing.JButton buttonInventory;
    private javax.swing.JButton buttonOrders;
    private javax.swing.JButton buttonDeliveries;
    private javax.swing.JButton buttonContacts;
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JButton buttonSubmit;
    private javax.swing.JButton buttonClose;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel updatePanel;
    private javax.swing.JPanel updateContainer;
    private javax.swing.JTabbedPane tablePane;
    private javax.swing.JScrollPane itemScrollPane;
    private javax.swing.JScrollPane salesScrollPane;
    private javax.swing.JScrollPane replenishScrollPane;
    private javax.swing.JScrollPane addressScrollPane;
    private javax.swing.JScrollPane phoneScrollPane;
    private Connection conn;
    private java.util.List<JTextField> textfields;
    private java.util.List<JLabel> labels;
    private TableSet currentSet;
    private UpdateEntity currentUpdateEntity;
    private UpdateType currentUpdateType;
    private boolean updatePanelVisible;
    private javax.swing.JScrollPane currentScrollPane;
    private javax.swing.JComboBox<ComboItem> updateMenu;
    private javax.swing.JLabel updateMenuTitle;
}
