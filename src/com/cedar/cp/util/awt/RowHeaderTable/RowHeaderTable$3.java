// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

class RowHeaderTable$3 implements TreeExpansionListener {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$3(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void treeExpanded(TreeExpansionEvent event) {
      this.this$0.initTableColumnSizes(RowHeaderTable.accessMethodmRowTreeTable(this.this$0));
   }

   public void treeCollapsed(TreeExpansionEvent event) {
      this.this$0.initTableColumnSizes(RowHeaderTable.accessMethodmRowTreeTable(this.this$0));
   }
}
