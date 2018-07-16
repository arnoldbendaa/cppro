// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorkbookActions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WorkbookActions$24 implements ActionListener {

   // $FF: synthetic field
   final WorkbookActions this$0;


   WorkbookActions$24(WorkbookActions var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      if(!WorkbookActions.accessMethod000(this.this$0)) {
         Integer fontSize = (Integer)WorkbookActions.accessMethod400(this.this$0).getSelectedItem();
         String name = (String)WorkbookActions.accessMethod500(this.this$0).getSelectedItem();
         WorkbookActions.accessMethod100(this.this$0).setFormatFont(name, fontSize);
      }

   }
}
