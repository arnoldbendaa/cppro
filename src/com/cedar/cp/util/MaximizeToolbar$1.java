// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.MaximizeToolbar;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class MaximizeToolbar$1 extends ComponentAdapter {

   // $FF: synthetic field
   final MaximizeToolbar this$0;


   MaximizeToolbar$1(MaximizeToolbar var1) {
      this.this$0 = var1;
   }

   public void componentMoved(ComponentEvent e) {
      if(!MaximizeToolbar.accessMethod000(this.this$0)) {
         MaximizeToolbar.accessMethod302(this.this$0, MaximizeToolbar.accessMethod200(this.this$0).getLocation());
      }

   }

   public void componentResized(ComponentEvent e) {
      if(!MaximizeToolbar.accessMethod000(this.this$0)) {
         MaximizeToolbar.accessMethod102(this.this$0, MaximizeToolbar.accessMethod200(this.this$0).getSize());
      }

   }
}
