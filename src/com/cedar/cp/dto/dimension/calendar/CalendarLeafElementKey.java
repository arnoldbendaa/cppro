// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import java.io.Serializable;

public class CalendarLeafElementKey implements Serializable {

   private int mYear;
   private int mIndex;


   public CalendarLeafElementKey(int year, int index) {
      this.mYear = year;
      this.mIndex = index;
   }

   public int getYear() {
      return this.mYear;
   }

   public void setYear(int year) {
      this.mYear = year;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof CalendarLeafElementKey)) {
         return false;
      } else {
         CalendarLeafElementKey other = (CalendarLeafElementKey)obj;
         return this.mYear == other.mYear && this.mIndex == other.mIndex;
      }
   }

   public int hashCode() {
      return this.mYear + this.mIndex;
   }
}
