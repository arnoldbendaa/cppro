// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.frmwrk;

import java.io.Serializable;

public class CalendarLeafLevelUpdate implements Serializable {

   private int mOldCalendarLevel;
   private int mNewCalendarLevel;
   private int mYear;
   private int[][] mIds;


   public CalendarLeafLevelUpdate(int oldCalendarLevel, int newCalendarLevel, int year, int[][] ids) {
      this.mOldCalendarLevel = oldCalendarLevel;
      this.mNewCalendarLevel = newCalendarLevel;
      this.mYear = year;
      this.mIds = ids;
   }

   public int getOldCalendarLevel() {
      return this.mOldCalendarLevel;
   }

   public int getNewCalendarLevel() {
      return this.mNewCalendarLevel;
   }

   public int getYear() {
      return this.mYear;
   }

   public int[][] getIds() {
      return this.mIds;
   }

   public boolean isAllocation() {
      return this.mOldCalendarLevel < this.mNewCalendarLevel;
   }

   public boolean isSummation() {
      return !this.isAllocation();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("mOldCalendarLevel:" + this.mOldCalendarLevel + "\n");
      sb.append(", mNewCalendarLevel:" + this.mNewCalendarLevel + "\n");
      sb.append(", mYear:" + this.mYear + "\n");
      if(this.getIds() != null) {
         int[][] arr$ = this.getIds();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            int[] i = arr$[i$];
            sb.append("src:" + i[0] + " target:" + i[1] + " count:" + i[2] + "\n");
         }
      }

      return sb.toString();
   }
}
