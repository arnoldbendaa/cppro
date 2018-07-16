// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class ResourceBrowserDialog$1 implements ListSelectionListener {

   // $FF: synthetic field
   final ResourceBrowserDialog this$0;


   ResourceBrowserDialog$1(ResourceBrowserDialog var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent e) {
      if(!e.getValueIsAdjusting()) {
         int index = ResourceBrowserDialog.accessMethod000(this.this$0).getSelectedIndex();
         ResourceBrowserDialog.accessMethod200(this.this$0).setIcon(ResourceBrowserDialog.accessMethod100(this.this$0, index));
      }

   }
}
