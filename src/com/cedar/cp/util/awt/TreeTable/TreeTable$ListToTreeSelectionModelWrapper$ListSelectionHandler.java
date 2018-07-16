// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTable$ListToTreeSelectionModelWrapper;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class TreeTable$ListToTreeSelectionModelWrapper$ListSelectionHandler implements ListSelectionListener {

   // $FF: synthetic field
   final TreeTable$ListToTreeSelectionModelWrapper this$1;


   TreeTable$ListToTreeSelectionModelWrapper$ListSelectionHandler(TreeTable$ListToTreeSelectionModelWrapper var1) {
      this.this$1 = var1;
   }

   public void valueChanged(ListSelectionEvent e) {
      this.this$1.updateSelectedPathsFromSelectedRows();
   }
}
