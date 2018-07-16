// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.flatform.gui.format.NumberPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class NumberPanel$1 implements ListSelectionListener {

   // $FF: synthetic field
   final NumberPanel this$0;


   NumberPanel$1(NumberPanel var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent e) {
      if(!e.getValueIsAdjusting()) {
         int index = NumberPanel.accessMethod000(this.this$0).getSelectedIndex();
         if(index < 0) {
            NumberPanel.accessMethod100(this.this$0).setText("Please select a category");
            NumberPanel.accessMethod300(this.this$0).show(NumberPanel.accessMethod200(this.this$0), "Empty");
         } else {
            NumberPanel.accessMethod100(this.this$0).setText(NumberPanel.accessMethod400(this.this$0)[index]);
            NumberPanel.accessMethod300(this.this$0).show(NumberPanel.accessMethod200(this.this$0), NumberPanel.accessMethod500(this.this$0)[index]);
         }
      }

   }
}
