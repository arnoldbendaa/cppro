// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class WorksheetPanel$6 extends MouseAdapter {

   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$6(WorksheetPanel var1) {
      this.this$0 = var1;
   }

   public void mouseReleased(MouseEvent e) {
      this.checkPopupTrigger(e);
   }

   public void mousePressed(MouseEvent e) {
      this.checkPopupTrigger(e);
   }

   private void checkPopupTrigger(MouseEvent e) {
      if(e.isPopupTrigger()) {
         WorksheetPanel.accessMethod200(this.this$0, e);
      }

   }
}
