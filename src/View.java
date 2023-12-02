import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import net.proteanit.sql.DbUtils;
public class View extends javax.swing.JFrame {

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

        tablePane = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        updatePanel = new javax.swing.JPanel();
        buttonInventory = new javax.swing.JButton();
        buttonOrders = new javax.swing.JButton();
        buttonDeliveries = new javax.swing.JButton();
        buttonContacts = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
	    buttonSubmit = new javax.swing.JButton();
	    buttonClose = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuEdit = new javax.swing.JMenu();
	    textfields = new ArrayList<JTextField>();
        topPanel = new topBar(this);
        rightSideBar = new RightSideBar();

        refreshScrollPane("SELECT * FROM Item");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuFile.setText("File");
        menuBar.add(menuFile);

        menuEdit.setText("Edit");
        menuBar.add(menuEdit);

        setJMenuBar(menuBar);

        buttonInventory.setText("Inventory");
        buttonInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshScrollPane("SELECT * FROM Item");
            }
        });

        buttonOrders.setText("Orders");
        buttonOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshScrollPane("SELECT * FROM ItemOrder");
            }
        });

        buttonDeliveries.setText("Deliveries");
        buttonDeliveries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshScrollPane("SELECT * FROM ReplenishOrder");
            }
        });

        buttonContacts.setText("Contacts");
        buttonContacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshScrollPane("SELECT * FROM PhoneList");
            }
        });

	buttonAdd.setText("Add");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
                   .addComponent(buttonContacts)
                   .addComponent(buttonAdd))
               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(tablePane)
                   .addComponent(this.topPanel.getTopBar()))
                   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                           .addComponent(this.rightSideBar.getRightSideBar())))
           .addComponent(updatePanel)
    );

	layout.setVerticalGroup(
   layout.createParallelGroup()
      .addGroup(layout.createSequentialGroup()
           .addComponent(buttonInventory)
           .addComponent(buttonOrders)
           .addComponent(buttonDeliveries)
           .addComponent(buttonContacts)
           .addComponent(buttonAdd))
      .addGroup(layout.createSequentialGroup()
              .addComponent(this.topPanel.getTopBar(), 30, 30, 30)
           .addComponent(tablePane)
           .addComponent(updatePanel))
           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                   .addComponent(this.rightSideBar.getRightSideBar()))
      );


      add(mainPanel);

        pack();
	setSize(640, 480);
    }

    public void refreshScrollPane(String arg) {
        try{
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(arg);
        tablePane.setViewportView(JTableDb.makeJTable(rs, this.conn));
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }


    private void postUpdatePanel() {

        GroupLayout layout = new GroupLayout(updatePanel);
        updatePanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // add textfields to list; we can make an enum with different cases and make a switch here
        textfields.add(new JTextField("Item"));
        textfields.add(new JTextField("Quantity"));

        GroupLayout.ParallelGroup textGroupH = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        for(JTextField text: textfields){
            textGroupH.addComponent(text);
        }
        GroupLayout.SequentialGroup textButtonGroupH = layout.createSequentialGroup().addComponent(buttonSubmit).addComponent(buttonClose);
    //	textGroupH.addGroup(layout.createSequentialGroup().addComponent(buttonSubmit).addComponent(buttonClose));
        layout.setHorizontalGroup(textGroupH.addGroup(textButtonGroupH));

        GroupLayout.SequentialGroup textGroupV = layout.createSequentialGroup();
        for(JTextField text: textfields){
            textGroupV.addComponent(text);
        }
        GroupLayout.ParallelGroup textButtonGroupV = layout.createParallelGroup().addComponent(buttonSubmit).addComponent(buttonClose);
    //	textGroupV.addGroup(layout.createParallelGroup().addComponent(buttonSubmit).addComponent(buttonClose));
        layout.setVerticalGroup(textGroupV.addGroup(textButtonGroupV));

        updatePanel.revalidate();
        updatePanel.repaint();
    }
    private void removeUpdatePanel() {
	textfields.clear();
	updatePanel.removeAll();
	updatePanel.revalidate();
	updatePanel.repaint();
    }




    private javax.swing.JButton buttonInventory;
    private javax.swing.JButton buttonOrders;
    private javax.swing.JButton buttonDeliveries;
    private javax.swing.JButton buttonContacts;
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonSubmit;
    private javax.swing.JButton buttonClose;

    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuBar menuBar;


    private javax.swing.JPanel mainPanel;

    private LeftSideBar leftSideBar;
    private RightSideBar rightSideBar;
    private topBar topPanel;

    private javax.swing.JPanel updatePanel;
    private javax.swing.JScrollPane tablePane;

    private Connection conn;
    private java.util.List<JTextField> textfields;
}
