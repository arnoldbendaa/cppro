// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.ExportException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractImpExpCommand {

   private PropertyChangeSupport mPropertyChangeSupport = null;


   public AbstractImpExpCommand() {
      this.mPropertyChangeSupport = new PropertyChangeSupport(this);
   }

   public void setPropertyChangeListener(PropertyChangeListener listener) {
      this.mPropertyChangeSupport.addPropertyChangeListener(listener);
   }

   public abstract void execute() throws ExportException;

   public PropertyChangeSupport getPropertyChangeSupport() {
      return this.mPropertyChangeSupport;
   }
}
