// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter;

class TreeTableModelAdapter$4 implements Runnable {

   // $FF: synthetic field
   final int val$row;
   // $FF: synthetic field
   final TreeTableModelAdapter this$0;


   TreeTableModelAdapter$4(TreeTableModelAdapter var1, int var2) {
      this.this$0 = var1;
      this.val$row = var2;
   }

   public void run() {
      this.this$0.fireTableRowsUpdated(this.val$row, this.val$row);
   }
}
