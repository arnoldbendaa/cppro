// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.SwingWorker;
import javax.swing.SwingUtilities;

class SwingWorker$2 implements Runnable {

   // $FF: synthetic field
   final Runnable val$doFinished;
   // $FF: synthetic field
   final SwingWorker this$0;


   SwingWorker$2(SwingWorker var1, Runnable var2) {
      this.this$0 = var1;
      this.val$doFinished = var2;
   }

   public void run() {
      try {
         SwingWorker.accessMethod000(this.this$0, this.this$0.construct());
      } finally {
         SwingWorker.accessMethod100(this.this$0).clear();
      }

      SwingUtilities.invokeLater(this.val$doFinished);
   }
}
