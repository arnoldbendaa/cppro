// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.InternalTableModel$1;
import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTableModel;
import com.cedar.cp.util.awt.TreeTable.TreeTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;

public class InternalTableModel extends AbstractTableModel {

   protected RowHeaderTableModel mRowHeaderModel;
   protected TreeTable mRowTreeTable;
   protected TreeExpansionListener mTreeExpansionListener;


   public InternalTableModel(RowHeaderTableModel model, TreeTable rowTreeTable) {
      this.mRowHeaderModel = model;
      this.mRowHeaderModel.setInternalTableModel(this);
      this.mRowTreeTable = rowTreeTable;
      this.mTreeExpansionListener = new InternalTableModel$1(this);
      this.mRowTreeTable.getTree().addTreeExpansionListener(this.mTreeExpansionListener);
   }

   public void setDisableTreeListeners(boolean disableTreeListeners) {
      if(disableTreeListeners) {
         this.mRowTreeTable.getTree().removeTreeExpansionListener(this.mTreeExpansionListener);
      } else {
         this.mRowTreeTable.getTree().addTreeExpansionListener(this.mTreeExpansionListener);
         this.fireTableChanged(new TableModelEvent(this));
      }

   }

   public Class getColumnClass(int column) {
      return this.mRowHeaderModel.getColumnClass(column);
   }

   public String getColumnName(int column) {
      return this.mRowHeaderModel.getColumnName(column);
   }

   public String getRowFullName(int row) {
      return this.mRowTreeTable.getTree().getPathForRow(row).toString();
   }

   public int getColumnCount() {
      return this.mRowHeaderModel.getColumnCount();
   }

   public int getRowCount() {
      return this.mRowTreeTable.getRowCount();
   }

   public boolean isCellEditable(int row, int column) {
      return this.mRowHeaderModel.isCellEditable(this.getNodeAtRow(row), column);
   }

   public Object getValueAt(int row, int column) {
      return this.mRowHeaderModel.getValueAt(this.getNodeAtRow(row), column);
   }

   public void setValueAt(Object input, int row, int column) {
      this.mRowHeaderModel.setValueAt(input, this.getNodeAtRow(row), column);
   }

   public Object getNodeAtRow(int row) {
      return row == -1?null:this.mRowTreeTable.getTree().getPathForRow(row).getLastPathComponent();
   }
}
