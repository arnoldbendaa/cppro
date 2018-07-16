// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.CellSelectionEvent;
import com.cedar.cp.util.flatform.gui.WorkbookPanel;
import com.cedar.cp.util.flatform.model.CellSelectionListener;

class WorkbookPanel$1 implements CellSelectionListener {

   // $FF: synthetic field
   final WorkbookPanel this$0;


   WorkbookPanel$1(WorkbookPanel var1) {
      this.this$0 = var1;
   }

   public void cellSelectionChanged(CellSelectionEvent e) {
      this.this$0.fireCellSelection(e);
   }
}
