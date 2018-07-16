// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.StatusBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StatusBar$7 implements ActionListener {

   // $FF: synthetic field
   final StatusBar this$0;


   StatusBar$7(StatusBar var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      StatusBar.accessMethod700(this.this$0).setText((String)null);
   }
}
