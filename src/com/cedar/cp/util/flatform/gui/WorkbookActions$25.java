// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorkbookActions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WorkbookActions$25 implements ActionListener {

   // $FF: synthetic field
   final WorkbookActions this$0;


   WorkbookActions$25(WorkbookActions var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      if(!WorkbookActions.accessMethod000(this.this$0)) {
         int layer = 0;
         if(WorkbookActions.accessMethod600(this.this$0).getSelectedIndex() > 0) {
            layer = WorkbookActions.accessMethod600(this.this$0).getSelectedIndex();
         }

         WorkbookActions.accessMethod100(this.this$0).setViewLayer(layer);
      }

   }
}
