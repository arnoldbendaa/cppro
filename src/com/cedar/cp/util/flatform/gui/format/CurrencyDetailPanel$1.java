// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.flatform.gui.format.CurrencyDetailPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;

class CurrencyDetailPanel$1 implements ActionListener {

   // $FF: synthetic field
   final CurrencyDetailPanel this$0;


   CurrencyDetailPanel$1(CurrencyDetailPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      Color newColor = JColorChooser.showDialog(this.this$0, "Negative Color", this.this$0.mTextColor);
      if(newColor != null) {
         this.this$0.mTextColor = newColor;
         CurrencyDetailPanel.accessMethod000(this.this$0).setForeground(newColor);
      }

   }
}
