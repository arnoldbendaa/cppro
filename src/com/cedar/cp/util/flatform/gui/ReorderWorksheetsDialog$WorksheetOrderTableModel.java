// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog$1;
import com.cedar.cp.util.flatform.model.Worksheet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

class ReorderWorksheetsDialog$WorksheetOrderTableModel extends AbstractTableModel {

   private List<String> mData;
   // $FF: synthetic field
   final ReorderWorksheetsDialog this$0;


   private ReorderWorksheetsDialog$WorksheetOrderTableModel(ReorderWorksheetsDialog var1) {
      this.this$0 = var1;
      this.mData = new ArrayList();
      Iterator i$ = ReorderWorksheetsDialog.accessMethod400(var1).getWorksheets().iterator();

      while(i$.hasNext()) {
         Worksheet worksheet = (Worksheet)i$.next();
         this.mData.add(worksheet.getName());
      }

   }

   public int getRowCount() {
      return this.mData.size();
   }

   public int getColumnCount() {
      return 1;
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      return this.mData.get(rowIndex);
   }

   public String getColumnName(int column) {
      return "Worksheet Name";
   }

   public List<String> getData() {
      return this.mData;
   }

   public void moveNorth(int row) {
      String value = (String)this.mData.remove(row);
      this.fireTableRowsDeleted(row, row);
      this.mData.add(row - 1, value);
      this.fireTableRowsInserted(row - 1, row - 1);
      ReorderWorksheetsDialog.accessMethod500(this.this$0).mTable.setRowSelectionInterval(row - 1, row - 1);
   }

   public void moveSouth(int row) {
      String value = (String)this.mData.remove(row);
      this.fireTableRowsDeleted(row, row);
      this.mData.add(row + 1, value);
      this.fireTableRowsInserted(row + 1, row + 1);
      ReorderWorksheetsDialog.accessMethod500(this.this$0).mTable.setRowSelectionInterval(row + 1, row + 1);
   }

   // $FF: synthetic method
   ReorderWorksheetsDialog$WorksheetOrderTableModel(ReorderWorksheetsDialog x0, ReorderWorksheetsDialog$1 x1) {
      this(x0);
   }
}
