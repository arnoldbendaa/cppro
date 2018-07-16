// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.ProcessingDialog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JProgressBar;

class ProcessingDialog$ProgressListener implements PropertyChangeListener {

   private JProgressBar progressBar;
   // $FF: synthetic field
   final ProcessingDialog this$0;


   private ProcessingDialog$ProgressListener(ProcessingDialog var1) {
      this.this$0 = var1;
   }

   ProcessingDialog$ProgressListener(ProcessingDialog var1, JProgressBar progressBar) {
      this.this$0 = var1;
      this.progressBar = progressBar;
      this.progressBar.setValue(0);
   }

   public void propertyChange(PropertyChangeEvent evt) {
      String strPropertyName = evt.getPropertyName();
      if("progress".equals(strPropertyName)) {
         this.progressBar.setIndeterminate(false);
         int progress = ((Integer)evt.getNewValue()).intValue();
         this.progressBar.setValue(progress);
      }

   }
}
