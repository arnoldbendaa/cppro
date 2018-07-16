// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.TaskListExportPanel$TaskListExportTableModel;
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

public class TaskListExportPanel extends CommonPanel {

   private JScrollPane scrollPane = new JScrollPane();
   private JTable table = new JTable();
   private TaskListExportPanel$TaskListExportTableModel tableModel = null;
   private GridBagLayout gridBagLayout = new GridBagLayout();
   private JLabel label = new JLabel();


   public TaskListExportPanel() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.gridBagLayout);
      this.label.setText("Task list:");
      this.scrollPane.getViewport().add(this.table);
      this.add(this.label, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 5), 0, 0));
      this.add(this.scrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
      this.table.setModel(this.getTableModel());
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.table, this.label, new String[]{"impexp.tasklistexport.table", null});//arnold
//      super.addQControl(qTreeTableControl);//arnold
   }

   public TaskListExportPanel$TaskListExportTableModel getTableModel() {
      if(this.tableModel == null) {
         this.tableModel = new TaskListExportPanel$TaskListExportTableModel(this);
      }

      return this.tableModel;
   }

   public String getName() {
      return "TaskListExportPanel";
   }

   public void init() {}

   public void setData(Collection itemList) {
      this.getTableModel().setModelData(itemList);
   }

   public void setData(CommonImpExpItem[] itemList) {}
}
