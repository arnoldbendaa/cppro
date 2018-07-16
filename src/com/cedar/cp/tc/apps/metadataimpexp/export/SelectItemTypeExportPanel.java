// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectItemTypeExportPanel$ItemTypeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
//import com.cedar.cp.tc.ctrl.QControl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelectItemTypeExportPanel extends CommonPanel {

   private JScrollPane scrollPane = new JScrollPane();
   private JTable itemTypeTable = new JTable();
   private JLabel label = new JLabel();
   private SelectItemTypeExportPanel$ItemTypeTableModel tableModel = null;
   private GridBagLayout gridBagLayout = new GridBagLayout();


   public SelectItemTypeExportPanel() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.gridBagLayout);
      this.label.setText("Select item type to export");
      this.scrollPane.getViewport().add(this.itemTypeTable);
      this.add(this.scrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.label, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
      this.scrollPane.getViewport().add(this.itemTypeTable);
      this.itemTypeTable.setModel(this.getTableModel());
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.itemTypeTable, this.label, new String[]{"impexp.selectitemtypeexport.itemtypetable", null});//arnold
//      super.addQControl(qTreeTableControl);//arnold
   }

   public String getName() {
      return "ExportPanelSelectItemType";
   }

   public void init() {}

   private SelectItemTypeExportPanel$ItemTypeTableModel getTableModel() {
      if(this.tableModel == null) {
         this.tableModel = new SelectItemTypeExportPanel$ItemTypeTableModel(this);
      }

      return this.tableModel;
   }

   public void setData(Collection itemList) {
      this.getTableModel().setModelData(itemList);
   }

   public void setData(CommonImpExpItem[] itemList) {}
}
