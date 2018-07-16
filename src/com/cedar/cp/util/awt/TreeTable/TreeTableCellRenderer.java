// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.ColorGradient;
import com.cedar.cp.util.awt.TreeTable.TreeTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class TreeTableCellRenderer extends JTree implements TableCellRenderer {

   private TreeTable mTreeTable;
   private ColorGradient mColorGradients;
   protected int visibleRow;
   private boolean mFastExpand = false;


   TreeTableCellRenderer(TreeTable treeTable, TreeModel model, int gradientDepth) {
      super(model);
      this.setLargeModel(false);
      this.mTreeTable = treeTable;
      this.mColorGradients = new ColorGradient(gradientDepth);
   }

   public void setGradientDepth(int gradientDepth) {
      this.mColorGradients = new ColorGradient(gradientDepth);
   }

   public TreeTable getTreeTable() {
      return this.mTreeTable;
   }

   public void updateUI() {
      super.updateUI();
      TreeCellRenderer tcr = this.getCellRenderer();
      if(tcr instanceof DefaultTreeCellRenderer) {
         DefaultTreeCellRenderer dtcr = (DefaultTreeCellRenderer)tcr;
         dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
         dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
         dtcr.setLeafIcon(UIManager.getIcon("Tree.leafIcon"));
         dtcr.setClosedIcon(UIManager.getIcon("Tree.closedIcon"));
         dtcr.setOpenIcon(UIManager.getIcon("Tree.openIcon"));
      }

   }

   public void setRowHeight(int rowHeight) {
      if(rowHeight > 0) {
         super.setRowHeight(rowHeight);
         if(this.mTreeTable != null && this.mTreeTable.getRowHeight() != rowHeight) {
            this.mTreeTable.setRowHeight(this.getRowHeight());
         }
      }

   }

   public void setBounds(int x, int y, int w, int h) {
      super.setBounds(x, 0, w, this.mTreeTable.getHeight());
   }

   public void paint(Graphics g) {
      g.translate(0, -this.visibleRow * this.getRowHeight());
      super.paint(g);
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(isSelected) {
         this.setBackground(table.getSelectionBackground());
      } else {
         Color bgColor = table.getBackground();
         if(value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value;
            int level = treeNode.getLevel() - 1;
            bgColor = this.mColorGradients.getColorAtDepth(bgColor, level);
         }

         this.setBackground(bgColor);
      }

      this.visibleRow = row;
      return this;
   }

   public Dimension getPreferredSize() {
      return super.getPreferredSize();
   }

   public void setFastExpandNodes(boolean fastExpand) {
      this.mFastExpand = fastExpand;
   }

   public void fireTreeExpanded(TreePath path) {
      if(!this.mFastExpand) {
         super.fireTreeExpanded(path);
      }

   }
}
