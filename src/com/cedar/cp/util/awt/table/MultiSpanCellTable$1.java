// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.table;

import com.cedar.cp.util.awt.TableUtilities;
import com.cedar.cp.util.awt.table.MultiSpanCellTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MultiSpanCellTable$1 extends MouseAdapter {

   // $FF: synthetic field
   final MultiSpanCellTable this$0;


   MultiSpanCellTable$1(MultiSpanCellTable var1) {
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent e) {
      int col;
      if(e.getClickCount() > 1) {
         col = this.this$0.getHitColumn(e.getPoint(), true);
         if(col >= 0) {
            TableUtilities.initTableColumnSize(this.this$0, col);
         }
      } else {
         col = this.this$0.getHitColumn(e.getPoint(), false);
         if(col > -1 && this.this$0.getRowCount() > 0) {
            this.this$0.setColumnSelectionInterval(col, col);
            this.this$0.setRowSelectionInterval(0, this.this$0.getRowCount() - 1);
         }
      }

   }
}
