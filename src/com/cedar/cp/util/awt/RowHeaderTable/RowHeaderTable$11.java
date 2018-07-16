// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class RowHeaderTable$11 implements ListSelectionListener {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$11(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent e) {
      if(!RowHeaderTable.accessMethodmProcessingSelectionEvent(this.this$0)) {
         RowHeaderTable.accessMethodmProcessingSelectionEvent(this.this$0, true);
         RowHeaderTable.accessMethodmRowTreeTable(this.this$0).getTree().setSelectionRows(RowHeaderTable.accessMethodmTable(this.this$0).getSelectedRows());
         RowHeaderTable.accessMethodmProcessingSelectionEvent(this.this$0, false);
      }

   }
}
