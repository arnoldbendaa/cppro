// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.SwingWorker;

class SwingWorker$1 implements Runnable {

   // $FF: synthetic field
   final SwingWorker this$0;


   SwingWorker$1(SwingWorker var1) {
      this.this$0 = var1;
   }

   public void run() {
      this.this$0.finished();
   }
}
