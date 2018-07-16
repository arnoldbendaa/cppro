// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.virement;


public class SpreadProfileLine {

   private boolean mHeld;
   private int mWeighting;


   public SpreadProfileLine(boolean held, int weighting) {
      this.mHeld = held;
      this.mWeighting = weighting;
   }

   public boolean isHeld() {
      return this.mHeld;
   }

   public void setHeld(boolean held) {
      this.mHeld = held;
   }

   public int getWeighting() {
      return this.mWeighting;
   }

   public void setWeighting(int weighting) {
      this.mWeighting = weighting;
   }
}
