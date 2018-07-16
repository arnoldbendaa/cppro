// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.MetaDataImportApplication;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.ImportItemsTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.TreeUtils;
//import com.cedar.cp.tc.ctrl.QControl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.tree.TreeNode;

public class ImportItemSelectionPanel extends CommonPanel {

   private JLabel importItemsLabel = new JLabel("Items to import:");
   private MetaDataImportApplication application = null;
   private ImportItemsTableModel importItemsTableModel = null;
   private JTreeTable treeTable = new JTreeTable();


   public ImportItemSelectionPanel(MetaDataImportApplication application) {
      this.application = application;
      this.setLayout(new GridBagLayout());
      JScrollPane scrollPane = new JScrollPane(this.treeTable);
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints.gridwidth = 0;
      gridBagConstraints.anchor = 17;
      this.add(this.importItemsLabel, gridBagConstraints);
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints2.gridwidth = 0;
      gridBagConstraints2.anchor = 10;
      gridBagConstraints2.fill = 1;
      gridBagConstraints2.weightx = 1.0D;
      gridBagConstraints2.weighty = 1.0D;
      this.add(scrollPane, gridBagConstraints2);
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.treeTable, this.importItemsLabel, new String[]{"impexp.importitemselection.treetable", null});//arnold
//      super.addQControl(qTreeTableControl);
   }

   public void init() {
	   //arnold all
//      try {
//         TreeNode e = TreeUtils.buildXMLFormTreeFromZipFile(this.application.getImportZIPFile());
//         this.importItemsTableModel = new ImportItemsTableModel(e);
//      } catch (Exception var2) {
//         var2.printStackTrace();
//      }
//
//      this.treeTable.init(this.importItemsTableModel);
   }

   public ImportItemsTableModel getImportItemsTableModel() {
      return this.importItemsTableModel;
   }

   public void setImportItemsTableModel(ImportItemsTableModel tableModel) {
      this.importItemsTableModel = tableModel;
   }

   public void setData(CommonImpExpItem[] listItemType) {}

   public String getName() {
      return "ItemSelectionPanel";
   }

   public void setData(Collection itemList) {}
}
