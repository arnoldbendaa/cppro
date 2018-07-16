// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectLookupTablesExportPanel$1;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectLookupTablesExportPanel$LookupTablesTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
//import com.cedar.cp.tc.ctrl.QControl;//arnold
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelectLookupTablesExportPanel extends CommonPanel {

   private JScrollPane mScrollPane = new JScrollPane();
   private JTable mExportItemTable = new JTable();
   private SelectLookupTablesExportPanel$LookupTablesTableModel mTableModel = null;
   private GridBagLayout mGridBagLayout = new GridBagLayout();
   private JLabel mTableLabel = new JLabel();


   public SelectLookupTablesExportPanel() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.mGridBagLayout);
      this.mTableLabel.setText("Select Lookup Table to export:");
      this.mScrollPane.getViewport().add(this.mExportItemTable);
      this.add(this.mTableLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 5), 0, 0));
      this.add(this.mScrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
      this.mExportItemTable.setModel(this.getTableModel());
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.mExportItemTable, this.mTableLabel, new String[]{"impexp.selectlookuptablesexport.exportitemtable", null});//arnold
//      super.addQControl(qTreeTableControl);//arnold
   }

   public String getName() {
      return "LookupTablesExportPanelSecondStep";
   }

   public void init() {}

   private SelectLookupTablesExportPanel$LookupTablesTableModel getTableModel() {
      if(this.mTableModel == null) {
         this.mTableModel = new SelectLookupTablesExportPanel$LookupTablesTableModel(this, (SelectLookupTablesExportPanel$1)null);
      }

      return this.mTableModel;
   }

   public void setData(CommonImpExpItem[] itemList) {}

   public void setData(Collection itemList) {
      this.getTableModel().setModelData(itemList);
   }
}
