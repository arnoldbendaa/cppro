// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.TaskListExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

class TaskListExportPanel$TaskListExportTableModel extends DefaultTableModel {

   private ArrayList mTaskList;
   // $FF: synthetic field
   final TaskListExportPanel this$0;


   public TaskListExportPanel$TaskListExportTableModel(TaskListExportPanel var1) {
      this.this$0 = var1;
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }

   public void setModelData(Collection itemList) {
      this.mTaskList = (ArrayList)itemList;
      Vector columnIdentifiers = new Vector();
      columnIdentifiers.add("No.");
      columnIdentifiers.add("Task");
      Vector dataVector = new Vector();
      int taskNo = 1;
      Iterator item = itemList.iterator();

      while(item.hasNext()) {
         CommonImpExpItem task = (CommonImpExpItem)item.next();
         Vector vecRow = new Vector();
         vecRow.add(Integer.valueOf(taskNo++));
         vecRow.add(task.getItemName());
         dataVector.add(vecRow);
      }

      this.setDataVector(dataVector, columnIdentifiers);
   }
}
