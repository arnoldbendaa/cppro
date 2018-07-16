// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;
import com.cedar.cp.util.awt.StatusBar$2;

class StatusBar$2$1 implements Runnable {

   // $FF: synthetic field
   final String val$msg;
   // $FF: synthetic field
   final StatusBar$2 this$1;


   StatusBar$2$1(StatusBar$2 var1, String var2) {
      this.this$1 = var1;
      this.val$msg = var2;
   }

   public void run() {
      StatusBar.accessMethod100(this.this$1.this$0).setText(this.val$msg);
   }
}
