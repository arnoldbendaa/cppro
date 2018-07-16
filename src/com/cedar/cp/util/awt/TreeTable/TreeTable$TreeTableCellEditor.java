// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.AbstractCellEditor;
import com.cedar.cp.util.awt.TreeTable.TreeTable;
import com.cedar.cp.util.awt.TreeTable.TreeTableModel;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TreeTable$TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {

   // $FF: synthetic field
   final TreeTable this$0;


   public TreeTable$TreeTableCellEditor(TreeTable var1) {
      this.this$0 = var1;
   }

   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
      return this.this$0.mTree;
   }

   public boolean isCellEditable(EventObject e) {
      if(e instanceof MouseEvent) {
         for(int counter = this.this$0.getColumnCount() - 1; counter >= 0; --counter) {
            if(this.this$0.getColumnClass(counter) == TreeTableModel.class) {
               MouseEvent me = (MouseEvent)e;
               MouseEvent newME = new MouseEvent(this.this$0.mTree, me.getID(), me.getWhen(), me.getModifiers(), me.getX() - this.this$0.getCellRect(0, counter, true).x, me.getY(), me.getClickCount(), me.isPopupTrigger());
               this.this$0.mTree.dispatchEvent(newME);
               break;
            }
         }
      }

      return false;
   }
}
