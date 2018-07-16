// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModelAdapter;

class TreeTableModelAdapter$3 implements Runnable {

   // $FF: synthetic field
   final TreeTableModelAdapter this$0;


   TreeTableModelAdapter$3(TreeTableModelAdapter var1) {
      this.this$0 = var1;
   }

   public void run() {
      this.this$0.fireTableDataChanged();
   }
}
