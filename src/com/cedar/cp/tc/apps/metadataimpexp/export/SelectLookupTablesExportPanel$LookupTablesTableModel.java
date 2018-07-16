// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectLookupTablesExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectLookupTablesExportPanel$1;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

class SelectLookupTablesExportPanel$LookupTablesTableModel extends DefaultTableModel implements TableModelListener {

   private ArrayList mLookupTableList;
   private static final int SELECT_COLUMN = 0;
   private static final int ITEM_NAME_COLUMN = 1;
   private static final int SELECT_INCL_DATA_COLUMN = 3;
   private static final int SELECT_INCL_STRUC_COLUMN = 4;
   // $FF: synthetic field
   final SelectLookupTablesExportPanel this$0;


   private SelectLookupTablesExportPanel$LookupTablesTableModel(SelectLookupTablesExportPanel var1) {
      this.this$0 = var1;
      this.mLookupTableList = null;
   }

   public Class getColumnClass(int c) {
      return this.getValueAt(0, c).getClass();
   }

   public boolean isCellEditable(int row, int col) {
      return col != 1;
   }

   public void tableChanged(TableModelEvent e) {
      if(this.mLookupTableList != null) {
         int row = e.getFirstRow();
         int column = e.getColumn();
         Object data = this.getValueAt(row, column);
         if(data instanceof Boolean) {
            boolean newValue = ((Boolean)data).booleanValue();
            CommonImpExpItem lookupTableImpExpItem = (CommonImpExpItem)this.mLookupTableList.get(row);
            switch(column) {
            case 0:
               lookupTableImpExpItem.setSelected(newValue);
            }
         }

      }
   }

   public void setModelData(Collection lookupTableList) {
      this.mLookupTableList = (ArrayList)lookupTableList;
      this.removeTableModelListener(this);
      Vector columnIdentifiers = new Vector();
      columnIdentifiers.add("Select");
      columnIdentifiers.add("Lookup Table");
      columnIdentifiers.add("Description");
      Vector dataVector = new Vector();
      Iterator iterator = this.mLookupTableList.iterator();

      while(iterator.hasNext()) {
         CommonImpExpItem item = (CommonImpExpItem)iterator.next();
         Vector rowVector = new Vector();
         rowVector.add(Boolean.valueOf(item.isSelected()));
         rowVector.add(item.getItemName());
         rowVector.add(item.getDescription());
         dataVector.add(rowVector);
      }

      this.setDataVector(dataVector, columnIdentifiers);
      this.addTableModelListener(this);
   }

   // $FF: synthetic method
   SelectLookupTablesExportPanel$LookupTablesTableModel(SelectLookupTablesExportPanel x0, SelectLookupTablesExportPanel$1 x1) {
      this(x0);
   }
}
