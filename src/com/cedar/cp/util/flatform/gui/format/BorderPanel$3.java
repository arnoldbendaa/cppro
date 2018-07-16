// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.gui.format.BorderPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;

class BorderPanel$3 implements ActionListener {

   // $FF: synthetic field
   final BorderPanel this$0;


   BorderPanel$3(BorderPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      Color newColor = JColorChooser.showDialog(this.this$0, "West Color", BorderPanel.accessMethod000(this.this$0).getWestColor());
      if(GeneralUtils.isDifferent(newColor, BorderPanel.accessMethod000(this.this$0).getWestColor())) {
         BorderPanel.accessMethod000(this.this$0).setColor(newColor, 7);
         BorderPanel.accessMethod102(this.this$0, true);
         this.this$0.repaint();
      }

   }
}
