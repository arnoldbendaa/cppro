// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;

class StatusBar$6 implements Runnable {

   // $FF: synthetic field
   final String val$msg;
   // $FF: synthetic field
   final int val$timeout;
   // $FF: synthetic field
   final StatusBar this$0;


   StatusBar$6(StatusBar var1, String var2, int var3) {
      this.this$0 = var1;
      this.val$msg = var2;
      this.val$timeout = var3;
   }

   public void run() {
      StatusBar.accessMethod600(this.this$0);
      StatusBar.accessMethod700(this.this$0).setText(this.val$msg);
      StatusBar.accessMethod700(this.this$0).paintImmediately(0, 0, StatusBar.accessMethod700(this.this$0).getWidth(), StatusBar.accessMethod700(this.this$0).getHeight());
      if(this.val$timeout > 0) {
         StatusBar.accessMethod800(this.this$0, this.val$timeout);
      }

   }
}
