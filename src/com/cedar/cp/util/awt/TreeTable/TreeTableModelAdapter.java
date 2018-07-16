// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModel;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter$1;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter$2;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter$3;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter$4;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

class TreeTableModelAdapter extends AbstractTableModel {

   private JTree mTree;
   private TreeTableModel mTreeTableModel;
   protected TreeExpansionListener mTreeExpansionListener;


   TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
      this.mTree = tree;
      this.mTreeTableModel = treeTableModel;
      this.mTreeExpansionListener = new TreeTableModelAdapter$1(this);
      tree.addTreeExpansionListener(this.mTreeExpansionListener);
      treeTableModel.addTreeModelListener(new TreeTableModelAdapter$2(this));
   }

   public void setDisableTreeListeners(boolean disableTreeListeners) {
      if(disableTreeListeners) {
         this.mTree.removeTreeExpansionListener(this.mTreeExpansionListener);
      } else {
         this.mTree.addTreeExpansionListener(this.mTreeExpansionListener);
         this.fireTableDataChanged();
      }

   }

   public TreeTableModel getTreeTableModel() {
      return this.mTreeTableModel;
   }

   public int getColumnCount() {
      return this.mTreeTableModel.getColumnCount();
   }

   public String getColumnName(int column) {
      return this.mTreeTableModel.getColumnName(column);
   }

   public Class getColumnClass(int column) {
      return this.mTreeTableModel.getColumnClass(column);
   }

   public int getRowCount() {
      return this.mTree.getRowCount();
   }

   protected Object nodeForRow(int row) {
      TreePath treePath = this.mTree.getPathForRow(row);
      return treePath != null?treePath.getLastPathComponent():null;
   }

   public Object getValueAt(int row, int column) {
      return this.mTreeTableModel.getValueAt(this.nodeForRow(row), column);
   }

   public boolean isCellEditable(int row, int column) {
      return this.mTreeTableModel.isCellEditable(this.nodeForRow(row), column);
   }

   public void setValueAt(Object value, int row, int column) {
      this.mTreeTableModel.setValueAt(value, this.nodeForRow(row), column);
   }

   protected void delayedFireTableDataChanged() {
      SwingUtilities.invokeLater(new TreeTableModelAdapter$3(this));
   }

   protected void delayedFireTableRowChanged(int row) {
      if(row != -1) {
         SwingUtilities.invokeLater(new TreeTableModelAdapter$4(this, row));
      }

   }

   // $FF: synthetic method
   static JTree accessMethod000(TreeTableModelAdapter x0) {
      return x0.mTree;
   }
}
