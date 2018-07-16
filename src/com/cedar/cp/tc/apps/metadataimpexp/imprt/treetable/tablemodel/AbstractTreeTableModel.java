// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public abstract class AbstractTreeTableModel implements TreeTableModel {

   protected Object root;
   protected EventListenerList listenerList = new EventListenerList();


   public AbstractTreeTableModel(Object root) {
      this.root = root;
   }

   public Object getRoot() {
      return this.root;
   }

   public boolean isLeaf(Object node) {
      return this.getChildCount(node) == 0;
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

   public void addTreeModelListener(TreeModelListener l) {
      this.listenerList.add(TreeModelListener.class, l);
   }

   public void removeTreeModelListener(TreeModelListener l) {
      this.listenerList.remove(TreeModelListener.class, l);
   }

   protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
      Object[] listeners = this.listenerList.getListenerList();
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
      Object[] listeners = this.listenerList.getListenerList();
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
      Object[] listeners = this.listenerList.getListenerList();
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
      Object[] listeners = this.listenerList.getListenerList();
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

   public boolean isCellEditable(Object node, int column) {
      return true;
   }
}
