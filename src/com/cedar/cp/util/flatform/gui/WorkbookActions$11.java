// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorkbookActions;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JColorChooser;

class WorkbookActions$11 extends AbstractAction {

   // $FF: synthetic field
   final WorkbookActions this$0;


   WorkbookActions$11(WorkbookActions var1, String x0, Icon x1) {
      super(x0, x1);
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      Color color = JColorChooser.showDialog(WorkbookActions.accessMethod100(this.this$0).getParentComponent(), "Text Color", (Color)null);
      if(color != null) {
         WorkbookActions.accessMethod300(this.this$0).setColor(color);
         WorkbookActions.accessMethod100(this.this$0).setFormatForeground(color);
      }

   }
}
