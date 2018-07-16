// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModel;
import com.cedar.cp.util.awt.TreeTable.TreeTableModelListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public abstract class AbstractTreeTableModel implements TreeTableModel {

   protected Object mRoot;
   protected EventListenerList mListenerList;
   protected int mGradientDepth;


   public AbstractTreeTableModel() {
      this(1);
   }

   public AbstractTreeTableModel(int gradientDepth) {
      this.mListenerList = new EventListenerList();
      this.mGradientDepth = gradientDepth;
   }

   public AbstractTreeTableModel(Object root, int gradientDepth) {
      this.mListenerList = new EventListenerList();
      this.mRoot = root;
      this.mGradientDepth = gradientDepth;
   }

   public void setRoot(Object root) {
      this.mRoot = root;
   }

   public int getGradientDepth() {
      return this.mGradientDepth;
   }

   public int getChildCount(Object node) {
      TreeNode tNode = (TreeNode)node;
      return tNode.getChildCount();
   }

   public Object getChild(Object node, int i) {
      TreeNode tNode = (TreeNode)node;
      return tNode.getChildAt(i);
   }

   public boolean isLeaf(Object node) {
      TreeNode tNode = (TreeNode)node;
      return tNode.isLeaf();
   }

   public Object getRoot() {
      return this.mRoot;
   }

   public void valueForPathChanged(TreePath path, Object newValue) {}

   public int getIndexOfChild(Object parent, Object child) {
      for(int i = 0; i < this.getChildCount(parent); ++i) {
         if(this.getChild(parent, i).equals(child)) {
            return i;
         }
      }

      return -1;
   }

   public void addTreeTableModelListener(TreeTableModelListener listener) {
      this.mListenerList.add(TreeTableModelListener.class, listener);
   }

   public void removeTreeTableModelListener(TreeTableModelListener listener) {
      this.mListenerList.remove(TreeTableModelListener.class, listener);
   }

   public void addTreeModelListener(TreeModelListener l) {
      this.mListenerList.add(TreeModelListener.class, l);
   }

   public void removeTreeModelListener(TreeModelListener l) {
      this.mListenerList.remove(TreeModelListener.class, l);
   }

   protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
      Object[] listeners = this.mListenerList.getListenerList();
      TreeModelEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == TreeModelListener.class) {
            if(e == null) {
               e = new TreeModelEvent(source, path, childIndices, children);
            }

            ((TreeModelListener)listeners[i + 1]).treeNodesChanged(e);
         }
      }

   }

   protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
      Object[] listeners = this.mListenerList.getListenerList();
      TreeModelEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == TreeModelListener.class) {
            if(e == null) {
               e = new TreeModelEvent(source, path, childIndices, children);
            }

            ((TreeModelListener)listeners[i + 1]).treeNodesInserted(e);
         }
      }

   }

   protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
      Object[] listeners = this.mListenerList.getListenerList();
      TreeModelEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == TreeModelListener.class) {
            if(e == null) {
               e = new TreeModelEvent(source, path, childIndices, children);
            }

            ((TreeModelListener)listeners[i + 1]).treeNodesRemoved(e);
         }
      }

   }

   protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
      Object[] listeners = this.mListenerList.getListenerList();
      TreeModelEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == TreeModelListener.class) {
            if(e == null) {
               e = new TreeModelEvent(source, path, childIndices, children);
            }

            ((TreeModelListener)listeners[i + 1]).treeStructureChanged(e);
         }
      }

   }

   public void fireTableDataChanged() {
      Object[] listeners = this.mListenerList.getListenerList();
      Object e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == TreeTableModelListener.class) {
            ((TreeTableModelListener)listeners[i + 1]).tableDataChanged();
         }
      }

   }

   public Class getColumnClass(int column) {
      return Object.class;
   }

   public boolean isCellEditable(Object node, int column) {
      return this.getColumnClass(column) == TreeTableModel.class;
   }

   public void setValueAt(Object aValue, Object node, int column) {}
}
