// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportSelectDimAndHierPanel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

class ImportSelectDimAndHierPanel$DimAndHierTableModel extends DefaultTableModel implements TableModelListener {

   private ArrayList mDimHierList;
   private JTable table;
   // $FF: synthetic field
   final ImportSelectDimAndHierPanel this$0;


   public ImportSelectDimAndHierPanel$DimAndHierTableModel(ImportSelectDimAndHierPanel var1, JTable table) {
      this.this$0 = var1;
      this.mDimHierList = null;
      this.table = null;
      this.table = table;
   }

   public Class getColumnClass(int c) {
      return this.getValueAt(0, c).getClass();
   }

   public boolean isCellEditable(int row, int col) {
      return true;
   }

   public void setValueAt(Object value, int row, int column) {
      super.setValueAt(value, row, column);
   }

   public void tableChanged(TableModelEvent e) {
      int row = e.getFirstRow();
      int column = e.getColumn();
      Object data = this.getValueAt(row, column);
      if(column == 0) {
         this.table.getColumnModel().getColumn(1).setCellEditor((TableCellEditor)ImportSelectDimAndHierPanel.accessMethod000(this.this$0).get(data.toString()));
         this.setValueAt(((List)ImportSelectDimAndHierPanel.accessMethod100(this.this$0).get(data.toString())).get(0), row, 1);
      }

   }

   public void setModelData(Collection itemList) {
      this.mDimHierList = (ArrayList)itemList;
      this.removeTableModelListener(this);
      Vector columnIdentifiers = new Vector();
      columnIdentifiers.add("Dimension");
      columnIdentifiers.add("Hierachy");
      Vector dataVector = new Vector();

      for(int i = 0; i < ImportSelectDimAndHierPanel.accessMethod200(this.this$0); ++i) {
         String dim = (String)ImportSelectDimAndHierPanel.accessMethod300(this.this$0).get(0);
         Vector vecRow = new Vector();
         vecRow.add(dim);
         List hierList = (List)ImportSelectDimAndHierPanel.accessMethod100(this.this$0).get(dim);
         if(hierList != null && hierList.size() > 0) {
            vecRow.add(hierList.get(0));
         } else {
            vecRow.add("");
         }

         dataVector.add(vecRow);
      }

      this.setDataVector(dataVector, columnIdentifiers);
      this.addTableModelListener(this);
   }
}
