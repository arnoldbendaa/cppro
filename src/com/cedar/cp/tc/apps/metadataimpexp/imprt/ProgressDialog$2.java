// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ProgressDialog;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.ProgressDialog$2$1;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ProgressDialog$2 extends WindowAdapter {

   // $FF: synthetic field
   final ProgressDialog this$0;


   ProgressDialog$2(ProgressDialog var1) {
      this.this$0 = var1;
   }

   public void windowActivated(WindowEvent e) {
      this.this$0.removeWindowListener(this);
      ProgressDialog$2$1 t = new ProgressDialog$2$1(this, "ProgressDialogWorker");
      t.start();
   }
}
