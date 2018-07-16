// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.view;

import com.cedar.cp.tc.apps.metadataimpexp.view.ButtonsPanel;
//import com.cedar.cp.tc.ctrl.ContextHelpWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonsPanel$1 implements ActionListener {

   // $FF: synthetic field
   final ButtonsPanel this$0;


   ButtonsPanel$1(ButtonsPanel var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      try {
         Object ex = e.getSource();
//         ContextHelpWindow.trackContextHelp(this.this$0.getRootPane());//arnold
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
