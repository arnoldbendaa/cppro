// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.CalculatorDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class CalculatorDialog$2 extends WindowAdapter {

   // $FF: synthetic field
   final CalculatorDialog this$0;


   CalculatorDialog$2(CalculatorDialog var1) {
      this.this$0 = var1;
   }

   public void windowActivated(WindowEvent e) {
      CalculatorDialog.accessMethod000(this.this$0).requestFocusInWindow();
   }
}
