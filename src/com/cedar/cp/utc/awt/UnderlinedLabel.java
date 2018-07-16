// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.utc.awt.UnderlinedLabel$1;
import java.awt.Graphics;
import javax.swing.JLabel;

public class UnderlinedLabel extends JLabel {

   private boolean mCursorOver;
   private boolean mRollOver;


   public UnderlinedLabel(String text, boolean rollOver) {
      super(text);
      this.mRollOver = rollOver;
      this.addMouseListener(new UnderlinedLabel$1(this));
   }

   public boolean isRollOver() {
      return this.mRollOver;
   }

   public void setRollOver(boolean rollOver) {
      this.mRollOver = rollOver;
   }

   public void paint(Graphics g) {
      super.paint(g);
      if(!this.mRollOver || this.mCursorOver) {
         g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
      }

   }

   // $FF: synthetic method
   static boolean access$002(UnderlinedLabel x0, boolean x1) {
      return x0.mCursorOver = x1;
   }
}
