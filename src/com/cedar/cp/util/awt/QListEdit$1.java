// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.QListEdit;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class QListEdit$1 implements ListSelectionListener {

   // $FF: synthetic field
   final QListEdit this$0;


   QListEdit$1(QListEdit var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent e) {
      this.this$0.updateUIState();
   }
}
