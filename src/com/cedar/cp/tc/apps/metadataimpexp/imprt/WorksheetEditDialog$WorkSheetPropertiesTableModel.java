// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.WorksheetEditDialog;
import com.cedar.cp.util.flatform.model.Properties;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

class WorksheetEditDialog$WorkSheetPropertiesTableModel extends DefaultTableModel {

   private Vector columnIdentifiers;
   private Vector dataVector;
   // $FF: synthetic field
   final WorksheetEditDialog this$0;


   public WorksheetEditDialog$WorkSheetPropertiesTableModel(WorksheetEditDialog var1) {
      this.this$0 = var1;
      this.columnIdentifiers = new Vector();
      this.dataVector = new Vector();
      this.columnIdentifiers.add("Property");
      this.columnIdentifiers.add("Value");
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }

   public void updateModelData(Properties properties) {
      this.dataVector.clear();
      Set keys = properties.keySet();
      Iterator i = keys.iterator();

      while(i.hasNext()) {
         Vector vecRow = new Vector();
         Object key = i.next();
         vecRow.add(key);
         vecRow.add(properties.get(key));
         this.dataVector.add(vecRow);
      }

      this.setDataVector(this.dataVector, this.columnIdentifiers);
   }
}
