// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.TableSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

class TableSorter$1 extends KeyAdapter {

   // $FF: synthetic field
   final TableSorter this$0;


   TableSorter$1(TableSorter var1) {
      this.this$0 = var1;
   }

   public void keyReleased(KeyEvent e) {
      if(e.isControlDown()) {
         int viewIndex = TableSorter.accessMethod000(this.this$0).getSelectedColumn();
         if(viewIndex == -1) {
            return;
         }

         int colIndex = TableSorter.accessMethod000(this.this$0).convertColumnIndexToModel(viewIndex);
         int code = e.getKeyCode();
         if(code == 38) {
            this.this$0.sortByColumn(colIndex, true);
         }

         if(code == 40) {
            this.this$0.sortByColumn(colIndex, false);
         }

         TableSorter.accessMethod000(this.this$0).setRowSelectionInterval(0, 0);
         TableSorter.accessMethod000(this.this$0).setColumnSelectionInterval(viewIndex, viewIndex);
      }

   }

   public void keyTyped(KeyEvent e) {
      char value = e.getKeyChar();
      if(!e.isConsumed() && !e.isControlDown() && !e.isAltDown() && !e.isActionKey() && !e.isMetaDown() && !e.isAltGraphDown() && Character.isLetterOrDigit(e.getKeyChar())) {
         Date timeNow = new Date();
         long elapsedTime = timeNow.getTime() - TableSorter.accessMethod100(this.this$0);
         if(elapsedTime > 400L) {
            TableSorter.accessMethod202(this.this$0, TableSorter.accessMethod000(this.this$0).getSelectedRow());
            TableSorter.accessMethod302(this.this$0, "" + Character.toLowerCase(e.getKeyChar()));
         } else {
            TableSorter.accessMethod302(this.this$0, TableSorter.accessMethod300(this.this$0) + Character.toLowerCase(e.getKeyChar()));
         }

         TableSorter.accessMethod102(this.this$0, timeNow.getTime());
         this.this$0.searchTableContents();
      }

   }
}
