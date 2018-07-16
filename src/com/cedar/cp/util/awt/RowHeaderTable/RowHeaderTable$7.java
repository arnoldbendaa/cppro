// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

class RowHeaderTable$7 implements TreeModelListener {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$7(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void treeNodesChanged(TreeModelEvent e) {
      TreePath path = e.getTreePath();
      int row = RowHeaderTable.accessMethodmRowTreeTable(this.this$0).getTree().getRowForPath(path);
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
