// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.flatform.gui.format.PercentageDetailPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;

class PercentageDetailPanel$1 implements ActionListener {

   // $FF: synthetic field
   final PercentageDetailPanel this$0;


   PercentageDetailPanel$1(PercentageDetailPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      Color newColor = JColorChooser.showDialog(this.this$0, "Negative Color", this.this$0.mTextColor);
      if(newColor != null) {
         this.this$0.mTextColor = newColor;
         PercentageDetailPanel.accessMethod000(this.this$0).setForeground(newColor);
      }

   }
}
