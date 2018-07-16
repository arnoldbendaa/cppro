// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.wizard;

import com.cedar.awt.wizard.WizardModel;
import com.cedar.awt.wizard.WizardModelListener;
import com.cedar.awt.wizard.WizardStep;
import java.beans.PropertyChangeSupport;
import javax.swing.event.EventListenerList;

public abstract class AbstractWizardModel implements WizardModel {

   protected EventListenerList mEventListener = new EventListenerList();
   protected PropertyChangeSupport mPropertyChangeSupport = new PropertyChangeSupport(this);


   public void addWizardModelIstener(WizardModelListener listener) {
      this.mEventListener.add(WizardModelListener.class, listener);
   }

   public void removeWizardModelIstener(WizardModelListener listener) {
      this.mEventListener.remove(WizardModelListener.class, listener);
   }

   public void fireStepUpdated(WizardStep step) {
      Object[] listeners = this.mEventListener.getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WizardModelListener.class) {
            ((WizardModelListener)listeners[i + 1]).stepUpdated(step);
         }
      }

   }

   public PropertyChangeSupport getPropertyChangeSupport() {
      return this.mPropertyChangeSupport;
   }
}
