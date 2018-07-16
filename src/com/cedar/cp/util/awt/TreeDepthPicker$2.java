// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.TreeDepthPicker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class TreeDepthPicker$2 extends MouseAdapter {

   // $FF: synthetic field
   final TreeDepthPicker this$0;


   TreeDepthPicker$2(TreeDepthPicker var1) {
      this.this$0 = var1;
   }

   public void mouseExited(MouseEvent e) {
      TreeDepthPicker.accessMethod102(this.this$0, -1);
      this.this$0.repaint();
   }

   public void mouseClicked(MouseEvent e) {
      if(e.getButton() == 1 && e.getClickCount() == 1) {
         int depth = TreeDepthPicker.accessMethod000(this.this$0, e);
         if(depth >= 0 && depth <= TreeDepthPicker.accessMethod200(this.this$0)) {
            if(depth == TreeDepthPicker.accessMethod200(this.this$0)) {
               TreeDepthPicker.accessMethod302(this.this$0, TreeDepthPicker.accessMethod200(this.this$0));
               this.this$0.fireTreeDepthSelected(999);
            } else if(depth < TreeDepthPicker.accessMethod200(this.this$0)) {
               TreeDepthPicker.accessMethod302(this.this$0, depth);
               this.this$0.fireTreeDepthSelected(depth);
            }
         } else {
            this.this$0.fireTreeDepthSelected(-1);
         }
      }

   }
}
