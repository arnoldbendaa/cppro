// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog$1;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog$2;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog$3;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog$4;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog$5;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog$WorkSheetPropertiesTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialogController;
import com.cedar.cp.util.flatform.model.Properties;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;

public class WorksheetEditDialog extends JDialog {

   private JPanel cardLayoutPanel = new JPanel(new CardLayout());
   private JSplitPane splitPane = new JSplitPane();
   private JPanel leftPanel = new JPanel();
   private JLabel wbStructureLabel = new JLabel();
   private JTree worksheetTree = null;
   private JPanel rightPanel = new JPanel();
   private JLabel wsProLabel = new JLabel();
   private JTable wsPropertiesTable = new JTable();
   private JPanel messageRightPanel = new JPanel();
   private JLabel messageLabel = new JLabel("Select worksheet on tree left panel to edit properties");
   private JPanel wsCommandPanel = new JPanel();
   private JButton editButton = new JButton();
   private JLabel paddingLabel = new JLabel();
   private JButton notUse = new JButton();
   private JButton restoreButton = new JButton();
   private JPanel bottomPane = new JPanel();
   private JButton okButton = new JButton();
   private JButton cancelButton = new JButton();
   private WorksheetEditDialog$WorkSheetPropertiesTableModel tableModel = null;
   private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Worksheets");
   private WorksheetEditDialogController mController = null;


   public WorksheetEditDialog(WorksheetEditDialogController controller) {
      this.setTitle("Edit FlatForm");
      this.mController = controller;
      this.worksheetTree = new JTree(this.root);

      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.getContentPane().setLayout(new GridBagLayout());
      this.splitPane.setBorder((Border)null);
      this.splitPane.setDividerSize(0);
      this.splitPane.setDividerLocation(170);
      this.leftPanel.setLayout(new GridBagLayout());
      this.wbStructureLabel.setText("Workbook structure");
      this.leftPanel.add(this.wbStructureLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 5, 0), 0, 0));
      JScrollPane treeScrollPane = new JScrollPane(this.worksheetTree);
      this.leftPanel.add(treeScrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 5), 0, 0));
      this.splitPane.add(this.leftPanel, "left");
      this.messageRightPanel.setLayout(new FlowLayout(0, 5, 10));
      this.messageRightPanel.add(this.messageLabel);
      this.rightPanel.setLayout(new GridBagLayout());
      this.wsProLabel.setText("Worksheet properties");
      this.rightPanel.add(this.wsProLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 0), 0, 0));
      JScrollPane wsTableScrollPane = new JScrollPane(this.wsPropertiesTable);
      this.rightPanel.add(wsTableScrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 5, 10, 0), 0, 0));
      this.wsCommandPanel.setLayout(new GridBagLayout());
      this.wsCommandPanel.setBorder((Border)null);
      this.editButton.setMinimumSize(new Dimension(50, 23));
      this.editButton.setPreferredSize(new Dimension(50, 23));
      this.editButton.setText("Edit");
      this.editButton.addActionListener(new WorksheetEditDialog$1(this));
      this.wsCommandPanel.add(this.editButton, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 5, 0, 0), 0, 0));
      this.wsCommandPanel.add(this.paddingLabel, new GridBagConstraints(1, 0, 1, 1, 1.0D, 1.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.notUse.setVisible(false);
      this.notUse.setEnabled(false);
      this.notUse.setMinimumSize(new Dimension(50, 23));
      this.notUse.setPreferredSize(new Dimension(50, 23));
      this.wsCommandPanel.add(this.notUse, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.restoreButton.setEnabled(false);
      this.restoreButton.setMinimumSize(new Dimension(50, 23));
      this.restoreButton.setPreferredSize(new Dimension(50, 23));
      this.restoreButton.setToolTipText("");
      this.restoreButton.setText("Restore");
      this.restoreButton.addActionListener(new WorksheetEditDialog$2(this));
      this.wsCommandPanel.add(this.restoreButton, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 5, 0, 0), 0, 0));
      this.rightPanel.add(this.wsCommandPanel, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 15, 1, new Insets(5, 0, 1, 0), 0, 0));
      this.cardLayoutPanel.add(this.messageRightPanel, "MSG");
      this.cardLayoutPanel.add(this.rightPanel, "PRO");
      this.splitPane.add(this.cardLayoutPanel, "right");
      this.getContentPane().add(this.splitPane, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 17, 1, new Insets(10, 10, 5, 10), 0, 0));
      this.bottomPane.setBorder(BorderFactory.createEtchedBorder());
      this.bottomPane.setLayout(new FlowLayout(2, 5, 15));
      this.okButton.setPreferredSize(new Dimension(50, 23));
      this.okButton.setText("OK");
      this.okButton.addActionListener(new WorksheetEditDialog$3(this));
      this.bottomPane.add(this.okButton);
      this.cancelButton.setPreferredSize(new Dimension(50, 23));
      this.cancelButton.setText("Cancel");
      this.cancelButton.addActionListener(new WorksheetEditDialog$4(this));
      this.bottomPane.add(this.cancelButton);
      this.getContentPane().add(this.bottomPane, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(10, 0, 0, 0), 0, 0));
      this.worksheetTree.addTreeSelectionListener(new WorksheetEditDialog$5(this));
      this.wsPropertiesTable.setModel(this.getTableModel());
   }

   private WorksheetEditDialog$WorkSheetPropertiesTableModel getTableModel() {
      if(this.tableModel == null) {
         this.tableModel = new WorksheetEditDialog$WorkSheetPropertiesTableModel(this);
      }

      return this.tableModel;
   }

   public void setWSPropertiesData(Properties properties) {
      this.getTableModel().updateModelData(properties);
   }

   public DefaultMutableTreeNode getTreeRoot() {
      return this.root;
   }

   public void refresh() {
      this.worksheetTree.expandRow(0);
   }

   public void enableRestoreButtons(boolean isEnable) {
      this.restoreButton.setEnabled(isEnable);
   }

   // $FF: synthetic method
   static WorksheetEditDialogController accessMethod000(WorksheetEditDialog x0) {
      return x0.mController;
   }

   // $FF: synthetic method
   static JTree accessMethod100(WorksheetEditDialog x0) {
      return x0.worksheetTree;
   }

   // $FF: synthetic method
   static JPanel accessMethod200(WorksheetEditDialog x0) {
      return x0.cardLayoutPanel;
   }
}
