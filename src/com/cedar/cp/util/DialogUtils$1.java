// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.DialogUtils;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class DialogUtils$1 extends ComponentAdapter {

   // $FF: synthetic field
   final DialogUtils this$0;


   DialogUtils$1(DialogUtils var1) {
      this.this$0 = var1;
   }

   public void componentMoved(ComponentEvent e) {
      if(!DialogUtils.accessMethod000(this.this$0)) {
         this.this$0.handleMoved();
      }

   }

   public void componentResized(ComponentEvent e) {
      if(!DialogUtils.accessMethod000(this.this$0)) {
         this.this$0.handleResized();
      }

   }
}
