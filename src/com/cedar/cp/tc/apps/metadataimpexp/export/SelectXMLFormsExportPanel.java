// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectXMLFormsExportPanel$XMLFormsTableModel;
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

public class SelectXMLFormsExportPanel extends CommonPanel {

   private JScrollPane scrollPane = new JScrollPane();
   private JTable exportItemTable = new JTable();
   private SelectXMLFormsExportPanel$XMLFormsTableModel tableModel = null;
   private GridBagLayout gridBagLayout = new GridBagLayout();
   private JLabel tableLabel = new JLabel();
   private static final int SELECTED_COLUMN = 0;


   public SelectXMLFormsExportPanel() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.gridBagLayout);
      this.tableLabel.setText("Select XMLForm to export:");
      this.scrollPane.getViewport().add(this.exportItemTable);
      this.add(this.tableLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 5), 0, 0));
      this.add(this.scrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
      this.exportItemTable.setModel(this.getTableModel());
      this.exportItemTable.setAutoscrolls(true);
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.exportItemTable, this.tableLabel, new String[]{"impexp.selectxmlformsexport.exportitemtable", null});//arnold
//      super.addQControl(qTreeTableControl);//arnold
   }

   public String getName() {
      return "XMLFormExportPanelSecondStep";
   }

   public void init() {}

   public void setData(CommonImpExpItem[] commonImpExpItem) {}

   private SelectXMLFormsExportPanel$XMLFormsTableModel getTableModel() {
      if(this.tableModel == null) {
         this.tableModel = new SelectXMLFormsExportPanel$XMLFormsTableModel(this);
      }

      return this.tableModel;
   }

   public void setData(Collection itemList) {
      this.getTableModel().setModelData(itemList);
   }
}
