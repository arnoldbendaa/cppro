// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.OkCancelDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

class OkCancelDialog$2 extends AbstractAction {

   // $FF: synthetic field
   final OkCancelDialog this$0;


   OkCancelDialog$2(OkCancelDialog var1, String x0) {
      super(x0);
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      this.this$0.handleCancel();
   }
}
