// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectItemTypeExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

class SelectItemTypeExportPanel$ItemTypeTableModel extends DefaultTableModel implements TableModelListener {

   private ArrayList mItemList;
   // $FF: synthetic field
   final SelectItemTypeExportPanel this$0;


   public SelectItemTypeExportPanel$ItemTypeTableModel(SelectItemTypeExportPanel var1) {
      this.this$0 = var1;
      this.mItemList = null;
   }

   public Class getColumnClass(int c) {
      return this.getValueAt(0, c).getClass();
   }

   public boolean isCellEditable(int row, int col) {
      return col == 0;
   }

   public void setValueAt(Object value, int row, int column) {
      super.setValueAt(value, row, column);
   }

   public void tableChanged(TableModelEvent e) {
      int row = e.getFirstRow();
      int column = e.getColumn();
      Object data = this.getValueAt(row, column);
      if(data instanceof Boolean) {
         ((CommonImpExpItem)this.mItemList.get(row)).setSelected(((Boolean)data).booleanValue());
      }

   }

   public void setModelData(Collection itemList) {
      this.mItemList = (ArrayList)itemList;
      this.removeTableModelListener(this);
      Vector columnIdentifiers = new Vector();
      columnIdentifiers.add("Select");
      columnIdentifiers.add("Type");
      Vector dataVector = new Vector();
      Iterator item = itemList.iterator();

      while(item.hasNext()) {
         CommonImpExpItem type = (CommonImpExpItem)item.next();
         Vector vecRow = new Vector();
         vecRow.add(Boolean.valueOf(type.isSelected()));
         vecRow.add(type.getItemName());
         dataVector.add(vecRow);
      }

      this.setDataVector(dataVector, columnIdentifiers);
      this.addTableModelListener(this);
   }
}
