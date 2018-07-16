// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.QSpinField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class QSpinField$3 implements DocumentListener {

   // $FF: synthetic field
   final QSpinField this$0;


   QSpinField$3(QSpinField var1) {
      this.this$0 = var1;
   }

   public void insertUpdate(DocumentEvent e) {
      QSpinField.accessMethod200(this.this$0);
   }

   public void removeUpdate(DocumentEvent e) {
      QSpinField.accessMethod200(this.this$0);
   }

   public void changedUpdate(DocumentEvent e) {
      QSpinField.accessMethod200(this.this$0);
   }
}
