// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;

class StatusBar$5 implements Runnable {

   // $FF: synthetic field
   final StatusBar this$0;


   StatusBar$5(StatusBar var1) {
      this.this$0 = var1;
   }

   public void run() {
      this.this$0.setStatusMessage("Ready");
      StatusBar.accessMethod500(this.this$0).paintImmediately(0, 0, StatusBar.accessMethod500(this.this$0).getWidth(), StatusBar.accessMethod500(this.this$0).getHeight());
   }
}
