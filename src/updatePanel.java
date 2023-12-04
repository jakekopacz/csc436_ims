import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import net.proteanit.sql.DbUtils;
public class updatePanel extends javax.swing.JPanel {

    private java.util.List<JTextField> textfields;
    private java.util.List<JLabel> labels;
    private javax.swing.JComboBox<String> updateMenu;
    private javax.swing.JLabel updateMenuTitle;

    /* post/remove update panel, and post/remove update content*/
    /*
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
*/
}