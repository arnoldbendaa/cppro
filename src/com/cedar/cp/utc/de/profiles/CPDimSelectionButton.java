// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.de.profiles;

import javax.swing.JButton;

public class CPDimSelectionButton extends JButton {

   private int mIndex;
   private boolean mCalendar;


   public CPDimSelectionButton(String text, int index) {
      super(text);
      this.mIndex = index;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public boolean isCalendar() {
      return this.mCalendar;
   }

   public void setCalendar(boolean calendar) {
      this.mCalendar = calendar;
   }
}
