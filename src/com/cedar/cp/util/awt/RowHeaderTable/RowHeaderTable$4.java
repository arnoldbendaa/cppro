// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class RowHeaderTable$4 extends MouseAdapter {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$4(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent e) {
      int col;
      if(e.getClickCount() > 1) {
         col = this.this$0.getHitColumn(e.getPoint(), true);
         if(col >= 0) {
            this.this$0.initTableColumnSize(RowHeaderTable.accessMethodmTable(this.this$0), col);
         }
      } else {
         col = this.this$0.getHitColumn(e.getPoint(), false);
         if(col > -1) {
            RowHeaderTable.accessMethodmTable(this.this$0).setColumnSelectionInterval(col, col);
            RowHeaderTable.accessMethodmTable(this.this$0).setRowSelectionInterval(0, this.this$0.getRowCount() - 1);
         }
      }

   }
}
