// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.flatform.gui.format.BorderPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BorderPanel$6 implements ActionListener {

   // $FF: synthetic field
   final BorderPanel this$0;


   BorderPanel$6(BorderPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      Integer thickness = (Integer)((Integer)BorderPanel.accessMethod500(this.this$0).getValue(BorderPanel.accessMethod600(this.this$0).getSelectedItem()));
      if(thickness != null && thickness.intValue() != BorderPanel.accessMethod000(this.this$0).getEastThickness()) {
         BorderPanel.accessMethod000(this.this$0).setThickness(thickness.intValue(), 3);
         BorderPanel.accessMethod102(this.this$0, true);
         this.this$0.repaint();
      }

   }
}
