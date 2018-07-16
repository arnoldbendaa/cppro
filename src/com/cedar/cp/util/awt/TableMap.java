// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableMap extends AbstractTableModel implements TableModelListener {

   protected TableModel mModel;


   public TableModel getModel() {
      return this.mModel;
   }

   public void setModel(TableModel model) {
      if(this.mModel != null) {
         this.mModel.removeTableModelListener(this);
      }

      if(model != null) {
         model.addTableModelListener(this);
      }

      this.mModel = model;
      this.fireTableStructureChanged();
   }

   public Object getValueAt(int aRow, int aColumn) {
      return this.mModel.getValueAt(aRow, aColumn);
   }

   public void setValueAt(Object aValue, int aRow, int aColumn) {
      this.mModel.setValueAt(aValue, aRow, aColumn);
   }

   public int getRowCount() {
      return this.mModel == null?0:this.mModel.getRowCount();
   }

   public int getColumnCount() {
      return this.mModel == null?0:this.mModel.getColumnCount();
   }

   public String getColumnName(int aColumn) {
      return this.mModel.getColumnName(aColumn);
   }

   public Class getColumnClass(int aColumn) {
      return this.mModel == null?null:this.mModel.getColumnClass(aColumn);
   }

   public boolean isCellEditable(int row, int column) {
      return this.mModel.isCellEditable(row, column);
   }

   public void tableChanged(TableModelEvent e) {
      this.fireTableChanged(e);
   }
}
