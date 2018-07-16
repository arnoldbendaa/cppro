// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

class RowHeaderTable$6 extends MouseAdapter {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$6(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent e) {
      if(e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
         int row = RowHeaderTable.accessMethodmRowTreeTable(this.this$0).rowAtPoint(e.getPoint());
         int col = RowHeaderTable.accessMethodmRowTreeTable(this.this$0).columnAtPoint(e.getPoint());
         if(row != -1 && col != -1) {
            RowHeaderTable.accessMethodmRowTreeTable(this.this$0).getSelectionModel().setSelectionInterval(row, row);
            RowHeaderTable.accessMethodmRowTreeTable(this.this$0).getColumnModel().getSelectionModel().setSelectionInterval(col, col);
            RowHeaderTable.accessMethodshowCellPopup(this.this$0, e, true, row, col);
         }
      }

   }
}
