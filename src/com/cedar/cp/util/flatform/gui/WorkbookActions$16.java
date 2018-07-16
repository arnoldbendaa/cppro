// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorkbookActions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

class WorkbookActions$16 extends AbstractAction {

   // $FF: synthetic field
   final WorkbookActions this$0;


   WorkbookActions$16(WorkbookActions var1, String x0, Icon x1) {
      super(x0, x1);
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      WorkbookActions.accessMethod100(this.this$0).setHorizontalAlignment(2);
   }
}
