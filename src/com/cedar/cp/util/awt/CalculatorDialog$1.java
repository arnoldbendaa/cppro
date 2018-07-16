// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.CalculatorDialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class CalculatorDialog$1 extends KeyAdapter {

   // $FF: synthetic field
   final CalculatorDialog this$0;


   CalculatorDialog$1(CalculatorDialog var1) {
      this.this$0 = var1;
   }

   public void keyPressed(KeyEvent key) {
      String action = null;
      if(key.getKeyCode() == 10) {
         action = "=";
      } else if(key.getKeyCode() == 67) {
         action = "C/CE";
      } else {
         action = String.valueOf(key.getKeyChar());
      }

      this.this$0.actionPerformed(new ActionEvent(this.this$0, 0, action));
      if(key.getKeyCode() == 10) {
         CalculatorDialog.accessMethod100(this.this$0).setText(CalculatorDialog.accessMethod000(this.this$0).getText());
         this.this$0.setVisible(false);
      }

   }
}
