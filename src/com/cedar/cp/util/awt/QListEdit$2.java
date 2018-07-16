// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.QListEdit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class QListEdit$2 extends MouseAdapter {

   // $FF: synthetic field
   final QListEdit this$0;


   QListEdit$2(QListEdit var1) {
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent e) {
      if(e.getClickCount() == 2 && this.this$0.mEdit.isEnabled()) {
         this.this$0.mEdit.doClick();
      }

   }
}
