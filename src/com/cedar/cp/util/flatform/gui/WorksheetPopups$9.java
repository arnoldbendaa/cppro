// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPopups;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

class WorksheetPopups$9 extends AbstractAction {

   // $FF: synthetic field
   final WorksheetPopups this$0;


   WorksheetPopups$9(WorksheetPopups var1, String x0) {
      super(x0);
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      WorksheetPopups.accessMethod100(this.this$0).clearContents(7);
   }
}
