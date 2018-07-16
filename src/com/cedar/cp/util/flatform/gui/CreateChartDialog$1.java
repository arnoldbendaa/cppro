// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.CreateChartDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CreateChartDialog$1 implements ActionListener {

   // $FF: synthetic field
   final CreateChartDialog this$0;


   CreateChartDialog$1(CreateChartDialog var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      CreateChartDialog.accessMethod100(this.this$0).setEnabled(CreateChartDialog.accessMethod000(this.this$0).getSelectedIndex() == 4);
   }
}
