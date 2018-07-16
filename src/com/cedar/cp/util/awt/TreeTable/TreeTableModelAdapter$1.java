// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

class TreeTableModelAdapter$1 implements TreeExpansionListener {

   // $FF: synthetic field
   final TreeTableModelAdapter this$0;


   TreeTableModelAdapter$1(TreeTableModelAdapter var1) {
      this.this$0 = var1;
   }

   public void treeExpanded(TreeExpansionEvent event) {
      this.this$0.fireTableDataChanged();
   }

   public void treeCollapsed(TreeExpansionEvent event) {
      this.this$0.fireTableDataChanged();
   }
}
