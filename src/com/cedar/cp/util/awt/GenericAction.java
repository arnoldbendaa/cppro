// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.event.EventListenerList;

public class GenericAction extends AbstractAction {

   private EventListenerList mListeners;


   public void actionPerformed(ActionEvent evt) {
      if(this.mListeners != null) {
         Object[] listenerList = this.mListeners.getListenerList();

         for(int i = 0; i <= listenerList.length - 2; i += 2) {
            ((ActionListener)listenerList[i + 1]).actionPerformed(evt);
         }
      }

   }

   public void addActionListener(ActionListener l) {
      if(this.mListeners == null) {
         this.mListeners = new EventListenerList();
      }

      this.mListeners.add(ActionListener.class, l);
   }

   public void removeActionListener(ActionListener l) {
      if(this.mListeners != null) {
         this.mListeners.remove(ActionListener.class, l);
      }
   }
}
