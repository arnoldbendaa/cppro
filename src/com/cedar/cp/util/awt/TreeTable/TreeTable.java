// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTable$ListToTreeSelectionModelWrapper;
import com.cedar.cp.util.awt.TreeTable.TreeTable$TreeTableCellEditor;
import com.cedar.cp.util.awt.TreeTable.TreeTableCellRenderer;
import com.cedar.cp.util.awt.TreeTable.TreeTableModel;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelListener;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class TreeTable extends JTable implements TreeTableModelListener {

   protected TreeTableCellRenderer mTree;
   private TreeTableModel mTreeTableModel;


   public TreeTable(TreeTableModel treeTableModel) {
      this.setModel(treeTableModel);
      this.setDefaultEditor(TreeTableModel.class, new TreeTable$TreeTableCellEditor(this));
      TreeTable$ListToTreeSelectionModelWrapper selectionWrapper = new TreeTable$ListToTreeSelectionModelWrapper(this);
      this.mTree.setSelectionModel(selectionWrapper);
      this.setSelectionModel(selectionWrapper.getListSelectionModel());
      this.setDefaultRenderer(TreeTableModel.class, this.mTree);
   }

   public void scrollPathToVisible(TreePath path) {
      this.mTree.expandPath(path.getParentPath());
   }

   public void expandRow(int row) {
      this.mTree.expandRow(row);
   }

   public void setDisplayFont(Font newFont) {
      this.setFont(newFont);
   }

   protected TreeTableCellRenderer createTreeTableCellRenderer(TreeTableModel treeTableModel) {
      return new TreeTableCellRenderer(this, treeTableModel, treeTableModel.getGradientDepth());
   }

   public void setModel(TreeTableModel treeTableModel) {
      if(this.mTree == null) {
         this.mTree = this.createTreeTableCellRenderer(treeTableModel);
      } else {
         this.mTree.setModel(treeTableModel);
      }

      this.mTree.setGradientDepth(treeTableModel.getGradientDepth());
      treeTableModel.addTreeTableModelListener(this);
      super.setModel(new TreeTableModelAdapter(treeTableModel, this.mTree));
      this.mTreeTableModel = treeTableModel;
   }

   public void setDisableTreeListeners(boolean disableTreeListeners) {
      TreeTableModelAdapter adapter = (TreeTableModelAdapter)this.getModel();
      adapter.setDisableTreeListeners(disableTreeListeners);
   }

   public void updateUI() {
      super.updateUI();
      if(this.mTree != null) {
         this.mTree.updateUI();
         TreeCellRenderer tcr = this.mTree.getCellRenderer();
         if(tcr instanceof DefaultTreeCellRenderer) {
            DefaultTreeCellRenderer dtcr = (DefaultTreeCellRenderer)tcr;
            dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
            dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
            dtcr.setBackgroundNonSelectionColor(this.getBackground());
         }
      }

   }

   public int getEditingRow() {
      return this.getColumnClass(this.editingColumn) == TreeTableModel.class?-1:this.editingRow;
   }

   public void setRowHeight(int rowHeight) {
      super.setRowHeight(rowHeight);
      if(this.mTree != null && this.mTree.getRowHeight() != rowHeight) {
         this.mTree.setRowHeight(this.getRowHeight());
      }

   }

   public JTree getTree() {
      return this.mTree;
   }

   public void tableDataChanged() {
      this.mTree.treeDidChange();
      this.repaint();
   }

   public TreeTableModel getTreeTableModel() {
      return this.mTreeTableModel;
   }
}
