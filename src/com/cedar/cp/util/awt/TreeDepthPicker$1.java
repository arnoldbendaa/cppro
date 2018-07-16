// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.TreeDepthPicker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class TreeDepthPicker$1 implements MouseMotionListener {

   // $FF: synthetic field
   final TreeDepthPicker this$0;


   TreeDepthPicker$1(TreeDepthPicker var1) {
      this.this$0 = var1;
   }

   public void mouseDragged(MouseEvent e) {}

   public void mouseMoved(MouseEvent e) {
      int depth = TreeDepthPicker.accessMethod000(this.this$0, e);
      if(depth != TreeDepthPicker.accessMethod100(this.this$0)) {
         TreeDepthPicker.accessMethod102(this.this$0, depth);
         this.this$0.repaint();
      }

   }
}
