// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CalendarYearSpecImpl implements CalendarYearSpec {

   private int mId;
   private int mYear;
   private boolean[] mSpec;


   public CalendarYearSpecImpl(int id, int year) {
      this.mSpec = new boolean[10];
      this.mId = id;
      this.mYear = year;
      this.mSpec[0] = true;
   }

   public CalendarYearSpecImpl(int id, int year, boolean[] spec) {
      this(id, year);
      this.mSpec = spec;
   }

   public CalendarYearSpecImpl(CalendarYearSpecImpl src) {
      this(src.getId(), src.getYear());
      this.mSpec = src.mSpec;
   }

   public int getYear() {
      return this.mYear;
   }

   public void setYear(int year) {
      this.mYear = year;
   }

   public boolean[] getSpec() {
      return mSpec;
   }
   
   public void setSpec(boolean[] spec) {
       this.mSpec = spec;
    }

   public boolean get(int calendarItemType) {
      return this.mSpec[calendarItemType];
   }

   public void set(int calendarItemType, boolean b) {
      this.mSpec[calendarItemType] = b;
      if(b) {
         if(calendarItemType == 4) {
            this.mSpec[1] = false;
            this.mSpec[2] = false;
            this.mSpec[3] = false;
         }

         if(calendarItemType == 1 || calendarItemType == 2 || calendarItemType == 3) {
            this.mSpec[4] = false;
         }
      }

   }

   public List<Integer> getLeafTypes() {
      ArrayList leaves = new ArrayList();
      if(this.mSpec[6]) {
         leaves.add(Integer.valueOf(6));
      }

      if(this.mSpec[5]) {
         leaves.add(Integer.valueOf(5));
      } else if(this.mSpec[4]) {
         leaves.add(Integer.valueOf(4));
      } else if(this.mSpec[3]) {
         leaves.add(Integer.valueOf(3));
      } else if(this.mSpec[2]) {
         leaves.add(Integer.valueOf(2));
      } else if(this.mSpec[1]) {
         leaves.add(Integer.valueOf(1));
      }

      if(this.mSpec[8]) {
         leaves.add(Integer.valueOf(8));
      }

      if(this.mSpec[9]) {
         leaves.add(Integer.valueOf(9));
      }

      if(this.mSpec[7]) {
         leaves.add(Integer.valueOf(7));
      }

      return leaves;
   }

   public boolean isLeaf(int calendarElementType) {
      return calendarElementType == 6?this.mSpec[6]:(calendarElementType == 7?this.mSpec[7]:(calendarElementType == 8?this.mSpec[8]:(calendarElementType == 9?this.mSpec[9]:(calendarElementType == 5 && this.mSpec[5]?true:(calendarElementType == 4 && this.mSpec[4] && !this.mSpec[5]?true:(calendarElementType == 3 && this.mSpec[3] && this.allFalse(this.mSpec, 4, 5)?true:(calendarElementType == 2 && this.mSpec[2] && this.allFalse(this.mSpec, 3, 5)?true:(calendarElementType == 1 && this.mSpec[1] && this.allFalse(this.mSpec, 2, 5)?true:calendarElementType == 0 && this.allFalse(this.mSpec, 1, 5)))))))));
   }

   public int getLeafLevel() {
      for(int i = 5; i >= 0; --i) {
         if(this.mSpec[i]) {
            return i;
         }
      }

      return 0;
   }

   private boolean allFalse(boolean[] flags, int startIndex, int endIndex) {
      for(int i = startIndex; i <= endIndex; ++i) {
         if(flags[i]) {
            return false;
         }
      }

      return true;
   }

   public boolean isCompatibleLeafLevel(CalendarYearSpecImpl cysi) {
      return cysi.get(7) == this.get(7) && cysi.get(6) == this.get(6) && cysi.get(8) == this.get(8) && cysi.get(9) == this.get(9) && cysi.getLeafLevel() == this.getLeafLevel();
   }

   public int getId() {
      return this.mId;
   }

   public void setId(int id) {
      this.mId = id;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<calendarYearSpec");
      XmlUtils.outputAttribute(out, "year", Integer.valueOf(this.mYear));

      for(int i = 0; i < this.mSpec.length; ++i) {
         XmlUtils.outputAttribute(out, "spec_" + i, Boolean.valueOf(this.mSpec[i]));
      }

      out.write("/> \n");
   }
}
