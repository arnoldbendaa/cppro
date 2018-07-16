// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

class TreeTableModelAdapter$2 implements TreeModelListener {

   // $FF: synthetic field
   final TreeTableModelAdapter this$0;


   TreeTableModelAdapter$2(TreeTableModelAdapter var1) {
      this.this$0 = var1;
   }

   public void treeNodesChanged(TreeModelEvent e) {
      TreePath path = e.getTreePath();
      int row = TreeTableModelAdapter.accessMethod000(this.this$0).getRowForPath(path);
      if(path != null && row != -1) {
         this.this$0.delayedFireTableRowChanged(row);
      }

   }

   public void treeNodesInserted(TreeModelEvent e) {
      this.this$0.delayedFireTableDataChanged();
   }

   public void treeNodesRemoved(TreeModelEvent e) {
      this.this$0.delayedFireTableDataChanged();
   }

   public void treeStructureChanged(TreeModelEvent e) {
      this.this$0.delayedFireTableDataChanged();
   }
}
