// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.QElementPickerModel;
import com.cedar.awt.hierarchy.AbstractHierarchyModel;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.EventListenerList;

public abstract class AbstractQElementPickerModel extends AbstractHierarchyModel implements QElementPickerModel, ItemSelectable {

   private EventListenerList mListenerList = new EventListenerList();


   public Object[] getSelectedObjects() {
      return null;
   }

   public void addItemListener(ItemListener l) {
      this.mListenerList.add(ItemListener.class, l);
   }

   public void removeItemListener(ItemListener l) {
      this.mListenerList.remove(ItemListener.class, l);
   }

   protected void fireItemListenerEvent(int stateChange, Object node) {
      Object[] listeners = this.mListenerList.getListenerList();
      ItemEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == ItemListener.class) {
            if(e == null) {
               e = new ItemEvent(this, 0, node, stateChange);
            }

            ((ItemListener)listeners[i + 1]).itemStateChanged(e);
         }
      }

   }
}
