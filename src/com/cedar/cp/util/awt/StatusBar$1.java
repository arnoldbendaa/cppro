// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class StatusBar$1 extends MouseAdapter {

   // $FF: synthetic field
   final StatusBar this$0;


   StatusBar$1(StatusBar var1) {
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent event) {
      if(event.getClickCount() == 2) {
         StatusBar.accessMethod000(this.this$0).debug("Running Garbage Collector");
         Runtime runTime = Runtime.getRuntime();
         runTime.gc();
         runTime.gc();
         runTime.gc();
         runTime.gc();
      }

   }
}
