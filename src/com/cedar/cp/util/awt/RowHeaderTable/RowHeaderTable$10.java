// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTable;
import java.awt.Rectangle;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

class RowHeaderTable$10 implements TreeSelectionListener {

   // $FF: synthetic field
   final RowHeaderTable this$0;


   RowHeaderTable$10(RowHeaderTable var1) {
      this.this$0 = var1;
   }

   public void valueChanged(TreeSelectionEvent e) {
      if(!RowHeaderTable.accessMethodmProcessingSelectionEvent(this.this$0)) {
         RowHeaderTable.accessMethodmProcessingSelectionEvent(this.this$0, true);
         int[] rows = RowHeaderTable.accessMethodmRowTreeTable(this.this$0).getSelectedRows();
         RowHeaderTable.accessMethodmTable(this.this$0).clearSelection();
         if(rows != null) {
            RowHeaderTable.accessMethodmTable(this.this$0).addColumnSelectionInterval(0, RowHeaderTable.accessMethodmTable(this.this$0).getColumnCount() - 1);

            int rHeight;
            for(rHeight = 0; rHeight < rows.length; ++rHeight) {
               int visRegion = rows[rHeight] >= RowHeaderTable.accessMethodmTable(this.this$0).getRowCount()?RowHeaderTable.accessMethodmTable(this.this$0).getRowCount() - 1:rows[rHeight];
               RowHeaderTable.accessMethodmTable(this.this$0).addRowSelectionInterval(visRegion, visRegion);
            }

            if(rows.length > 0) {
               rHeight = RowHeaderTable.accessMethodmTable(this.this$0).getRowHeight(rows[0]);
               Rectangle var5 = new Rectangle(0, rows[0] * rHeight, 10, rHeight);
               RowHeaderTable.accessMethodmTable(this.this$0).scrollRectToVisible(var5);
            }
         }

         RowHeaderTable.accessMethodmProcessingSelectionEvent(this.this$0, false);
      }

   }
}
