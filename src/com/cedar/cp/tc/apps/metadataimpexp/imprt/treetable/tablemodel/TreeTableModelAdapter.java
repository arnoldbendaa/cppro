// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModelAdapter$1;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class TreeTableModelAdapter extends AbstractTableModel {

   JTree jTree = null;
   TreeTableModel treeTableModel = null;


   public TreeTableModel getTreeTableModel() {
      return this.treeTableModel;
   }

   public void setTreeTableModel(TreeTableModel treeTableModel) {
      this.treeTableModel = treeTableModel;
   }

   public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
      this.jTree = tree;
      this.treeTableModel = treeTableModel;
      this.jTree.setModel(new DefaultTreeModel((TreeNode)treeTableModel.getRoot()));
      this.jTree.fireTreeExpanded(this.jTree.getPathForRow(0));
      this.jTree.addTreeExpansionListener(new TreeTableModelAdapter$1(this));
   }

   public int getColumnCount() {
      return this.treeTableModel.getColumnCount();
   }

   public String getColumnName(int column) {
      return this.treeTableModel.getColumnName(column);
   }

   public Class getColumnClass(int column) {
      return this.treeTableModel.getColumnClass(column);
   }

   public int getRowCount() {
      return this.jTree.getRowCount();
   }

   public Object nodeForRow(int row) {
      TreePath treePath = this.jTree.getPathForRow(row);
      return treePath.getLastPathComponent();
   }

   public Object getValueAt(int row, int column) {
      return this.treeTableModel.getValueAt(this.nodeForRow(row), column);
   }

   public boolean isCellEditable(int row, int column) {
      return this.treeTableModel.isCellEditable(this.nodeForRow(row), column);
   }

   public void setValueAt(Object value, int row, int column) {
      this.treeTableModel.setValueAt(value, this.nodeForRow(row), column);
      this.fireTableDataChanged();
   }
}
