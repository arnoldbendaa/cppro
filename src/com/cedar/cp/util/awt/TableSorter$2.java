// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.TableSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

class TableSorter$2 extends MouseAdapter {

   // $FF: synthetic field
   final TableSorter this$0;


   TableSorter$2(TableSorter var1) {
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent e) {
      TableColumnModel columnModel = TableSorter.accessMethod000(this.this$0).getColumnModel();
      int viewColumn = columnModel.getColumnIndexAtX(e.getX());
      int column = TableSorter.accessMethod000(this.this$0).convertColumnIndexToModel(viewColumn);
      if(column != -1 && SwingUtilities.isLeftMouseButton(e)) {
         Boolean sortSequence = (Boolean)TableSorter.accessMethod400(this.this$0).get(new Integer(column));
         if(sortSequence == null) {
            sortSequence = Boolean.FALSE;
         } else {
            sortSequence = sortSequence.booleanValue()?Boolean.FALSE:Boolean.TRUE;
         }

         TableSorter.accessMethod400(this.this$0).put(new Integer(column), sortSequence);
         this.this$0.sortByColumn(column, sortSequence.booleanValue());
         if(TableSorter.accessMethod000(this.this$0).getRowCount() > 0) {
            TableSorter.accessMethod000(this.this$0).setRowSelectionInterval(0, 0);
            TableSorter.accessMethod000(this.this$0).setColumnSelectionInterval(column, column);
         }
      }

   }
}
