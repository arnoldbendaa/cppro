// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import java.util.EventObject;
import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

public class AbstractCellEditor implements CellEditor {

   protected EventListenerList listenerList = new EventListenerList();


   public Object getCellEditorValue() {
      return null;
   }

   public boolean isCellEditable(EventObject e) {
      return true;
   }

   public boolean shouldSelectCell(EventObject anEvent) {
      return false;
   }

   public boolean stopCellEditing() {
      return true;
   }

   public void cancelCellEditing() {}

   public void addCellEditorListener(CellEditorListener l) {
      this.listenerList.add(CellEditorListener.class, l);
   }

   public void removeCellEditorListener(CellEditorListener l) {
      this.listenerList.remove(CellEditorListener.class, l);
   }

   protected void fireEditingStopped() {
      Object[] listeners = this.listenerList.getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == CellEditorListener.class) {
            ((CellEditorListener)listeners[i + 1]).editingStopped(new ChangeEvent(this));
         }
      }

   }

   protected void fireEditingCanceled() {
      Object[] listeners = this.listenerList.getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == CellEditorListener.class) {
            ((CellEditorListener)listeners[i + 1]).editingCanceled(new ChangeEvent(this));
         }
      }

   }
}
