// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;

class RowHeaderTable$8 implements Runnable {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$8(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void run() {
      RowHeaderTable.accessMethodmTableModel(this.this$0).fireTableDataChanged();
   }
}
