// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;
import com.cedar.cp.util.awt.StatusBar$2$1;
import javax.swing.SwingUtilities;

class StatusBar$2 extends Thread {

   // $FF: synthetic field
   final StatusBar this$0;


   StatusBar$2(StatusBar var1) {
      this.this$0 = var1;
   }

   public void run() {
      try {
         while(true) {
            Runtime ie = Runtime.getRuntime();
            long freeMemory = ie.freeMemory() / 1048576L;
            long totalMemory = ie.totalMemory() / 1048576L;
            long usedMemory = totalMemory - freeMemory;
            String msg = usedMemory + "M of " + totalMemory + "M";
            SwingUtilities.invokeLater(new StatusBar$2$1(this, msg));
            sleep(5000L);
         }
      } catch (InterruptedException var9) {
         ;
      }
   }
}
