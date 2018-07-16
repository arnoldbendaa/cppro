// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.utc.awt.UnderlinedLabel;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class UnderlinedLabel$1 extends MouseAdapter {

   // $FF: synthetic field
   final UnderlinedLabel this$0;


   UnderlinedLabel$1(UnderlinedLabel var1) {
      this.this$0 = var1;
   }

   public void mouseEntered(MouseEvent e) {
      UnderlinedLabel.access$002(this.this$0, true);
      this.this$0.setCursor(Cursor.getPredefinedCursor(12));
      this.this$0.repaint();
   }

   public void mouseExited(MouseEvent e) {
      UnderlinedLabel.access$002(this.this$0, false);
      this.this$0.setCursor(Cursor.getDefaultCursor());
      this.this$0.repaint();
   }
}
