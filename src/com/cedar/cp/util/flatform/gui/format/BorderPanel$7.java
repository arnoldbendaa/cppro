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

class BorderPanel$7 implements ActionListener {

   // $FF: synthetic field
   final BorderPanel this$0;


   BorderPanel$7(BorderPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      Color newColor = JColorChooser.showDialog(this.this$0, "South Color", BorderPanel.accessMethod000(this.this$0).getSouthColor());
      if(GeneralUtils.isDifferent(newColor, BorderPanel.accessMethod000(this.this$0).getSouthColor())) {
         BorderPanel.accessMethod000(this.this$0).setColor(newColor, 5);
         BorderPanel.accessMethod102(this.this$0, true);
         this.this$0.repaint();
      }

   }
}
