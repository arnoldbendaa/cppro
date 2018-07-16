// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ProgressDialog;
import javax.swing.JOptionPane;

class ProgressDialog$3 implements Runnable {

   // $FF: synthetic field
   final String val$msg;
   // $FF: synthetic field
   final ProgressDialog this$0;


   ProgressDialog$3(ProgressDialog var1, String var2) {
      this.this$0 = var1;
      this.val$msg = var2;
   }

   public void run() {
      JOptionPane.showMessageDialog(this.this$0, this.val$msg, "Processing problem", 0);
   }
}
