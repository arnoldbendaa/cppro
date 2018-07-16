// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ProgressDialog$2;

class ProgressDialog$2$1 extends Thread {

   // $FF: synthetic field
   final ProgressDialog$2 this$1;


   ProgressDialog$2$1(ProgressDialog$2 var1, String x0) {
      super(x0);
      this.this$1 = var1;
   }

   public void run() {
      try {
         this.this$1.this$0.doWork();
      } catch (Throwable var5) {
         var5.printStackTrace();
         this.this$1.this$0.showUserMessage("Unexpected error: \n" + var5.getMessage());
      } finally {
         this.this$1.this$0.dispose();
      }

   }
}
