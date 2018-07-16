// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.QSpinField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class QSpinField$1 implements ActionListener {

   // $FF: synthetic field
   final QSpinField this$0;


   QSpinField$1(QSpinField var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      QSpinField.accessMethod008(this.this$0);
      QSpinField.accessMethod100(this.this$0);
      this.this$0.fireItemStateChanged(1, new Integer(QSpinField.accessMethod000(this.this$0)));
      this.this$0.enableButtons();
   }
}
