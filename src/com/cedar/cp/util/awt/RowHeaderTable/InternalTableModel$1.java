// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.InternalTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

class InternalTableModel$1 implements TreeExpansionListener {

   // $FF: synthetic field
   final InternalTableModel this$0;


   InternalTableModel$1(InternalTableModel var1) {
      this.this$0 = var1;
   }

   public void treeExpanded(TreeExpansionEvent event) {
      this.this$0.fireTableChanged(new TableModelEvent(this.this$0));
   }

   public void treeCollapsed(TreeExpansionEvent event) {
      this.this$0.fireTableChanged(new TableModelEvent(this.this$0));
   }
}
