// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;

class RowHeaderTable$9 implements Runnable {

   // $FF: synthetic field
   final int val$row;
   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$9(RowHeaderTable var1, int var2) {
      this.this$0 = var1;
      this.val$row = var2;
   }

   public void run() {
      RowHeaderTable.accessMethodmTableModel(this.this$0).fireTableRowsUpdated(this.val$row, this.val$row);
   }
}
