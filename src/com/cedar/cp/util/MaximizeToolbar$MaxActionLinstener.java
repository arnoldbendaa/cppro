// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.MaximizeToolbar;
import com.cedar.cp.util.MaximizeToolbar$1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MaximizeToolbar$MaxActionLinstener implements ActionListener {

   // $FF: synthetic field
   final MaximizeToolbar this$0;


   private MaximizeToolbar$MaxActionLinstener(MaximizeToolbar var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      if(MaximizeToolbar.accessMethod000(this.this$0)) {
         MaximizeToolbar.accessMethod200(this.this$0).setSize(MaximizeToolbar.accessMethod100(this.this$0));
         MaximizeToolbar.accessMethod200(this.this$0).setLocation(MaximizeToolbar.accessMethod300(this.this$0));
         MaximizeToolbar.accessMethod002(this.this$0, false);
         MaximizeToolbar.accessMethod400(this.this$0, e);
      } else {
         MaximizeToolbar.maximise(MaximizeToolbar.accessMethod200(this.this$0));
         MaximizeToolbar.accessMethod002(this.this$0, true);
         MaximizeToolbar.accessMethod500(this.this$0, e);
      }

   }

   // $FF: synthetic method
   MaximizeToolbar$MaxActionLinstener(MaximizeToolbar x0, MaximizeToolbar$1 x1) {
      this(x0);
   }
}
