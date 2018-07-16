// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;

class StatusBar$3 implements Runnable {

   // $FF: synthetic field
   final boolean val$state;
   // $FF: synthetic field
   final StatusBar this$0;


   StatusBar$3(StatusBar var1, boolean var2) {
      this.this$0 = var1;
      this.val$state = var2;
   }

   public void run() {
      StatusBar.accessMethod400(this.this$0).setIcon(this.val$state?StatusBar.accessMethod200(this.this$0):StatusBar.accessMethod300(this.this$0));
      StatusBar.accessMethod400(this.this$0).paintImmediately(0, 0, StatusBar.accessMethod400(this.this$0).getWidth(), StatusBar.accessMethod400(this.this$0).getHeight());
   }
}
