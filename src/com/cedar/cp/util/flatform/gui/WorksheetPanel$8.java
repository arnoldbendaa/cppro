// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class WorksheetPanel$8 implements ListSelectionListener {

   // $FF: synthetic field
   final WorksheetPanel this$0;


   WorksheetPanel$8(WorksheetPanel var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent e) {
      if(!e.getValueIsAdjusting()) {
         WorksheetPanel.accessMethod400(this.this$0).setRowSelectionInterval(e.getLastIndex(), e.getLastIndex());
         WorksheetPanel.accessMethod400(this.this$0).setColumnSelectionInterval(0, WorksheetPanel.accessMethod400(this.this$0).getColumnCount() - 1);
      }

   }
}
