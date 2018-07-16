// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.utc.awt.ProgressDialog;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

class ProgressDialog$2 extends WindowAdapter {

   // $FF: synthetic field
   final ProgressDialog this$0;


   ProgressDialog$2(ProgressDialog var1) {
      this.this$0 = var1;
   }

   public void windowActivated(WindowEvent e) {
      try {
         this.this$0.doWork();
      } catch (Throwable var6) {
         JOptionPane.showMessageDialog((Component)null, "System detected internal error:\n" + var6.getMessage(), "Error Detected", 0);
      } finally {
         this.this$0.dispose();
      }

   }
}
