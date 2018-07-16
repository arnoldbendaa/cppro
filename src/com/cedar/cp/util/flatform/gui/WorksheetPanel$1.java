// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WorksheetPanel$1 implements ActionListener {

   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$1(WorksheetPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      this.this$0.toggleFormatBoldFont();
   }
}
