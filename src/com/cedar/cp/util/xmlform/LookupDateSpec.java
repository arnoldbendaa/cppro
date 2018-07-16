// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.sql.Timestamp;
import java.util.Calendar;

public class LookupDateSpec {

   private String mDate;
   private int mYearOffset = 0;
   private int mMonthOffset = 0;
   private int mDayOffset = 0;


   public LookupDateSpec(String sourceText) {
      String[] specTokens = sourceText.split(";");
      int yearOffsetIdx = -1;
      int monthOffsetIdx = -1;
      int dayOffsetIdx = -1;
      int dateIdx = -1;

      for(int i = 0; i < specTokens.length; ++i) {
         String e;
         if(specTokens[i].startsWith("year=")) {
            if(yearOffsetIdx > -1) {
               throw new IllegalArgumentException("duplicate year offset");
            }

            e = specTokens[i].substring(5);
            if(!this.isValidNumber(e)) {
               throw new IllegalArgumentException("year offset is not valid");
            }

            this.mYearOffset = Integer.parseInt(e);
            yearOffsetIdx = i;
         } else if(specTokens[i].startsWith("month=")) {
            if(monthOffsetIdx > -1) {
               throw new IllegalArgumentException("duplicate month offset");
            }

            e = specTokens[i].substring(6);
            if(!this.isValidNumber(e)) {
               throw new IllegalArgumentException("month offset is not valid");
            }

            this.mMonthOffset = (new Integer(e)).intValue();
            monthOffsetIdx = i;
         } else if(specTokens[i].startsWith("day=")) {
            if(dayOffsetIdx > -1) {
               throw new IllegalArgumentException("duplicate day offset");
            }

            try {
               this.mDayOffset = Integer.parseInt(specTokens[i].substring(7));
            } catch (NumberFormatException var9) {
               throw new IllegalArgumentException("day offset is not valid");
            }

            dayOffsetIdx = i;
         } else {
            if(!specTokens[i].matches("(yyyy|(\\d){1,4})($|/mm|/(\\d){1,2})($|/dd|/(\\d){1,2})") || dateIdx > -1) {
               throw new IllegalArgumentException(specTokens[i] + " not valid");
            }

            dateIdx = i;
            this.mDate = specTokens[i];
         }
      }

      if(dateIdx == -1) {
         throw new IllegalArgumentException("no date");
      }
   }

   private boolean isValidNumber(String s) {
      return s.matches("-\\d*|\\d*");
   }

   private Timestamp getActualDate(Integer calSeId, CalendarInfo calInfo, boolean isEndDate) {
      Calendar cal = Calendar.getInstance();
      if(calSeId != null && calInfo != null) {
         CalendarElementNode split = calInfo.getById(calSeId);
         cal.setTimeInMillis(split.getActualDate().getTime());
      }

      String[] split1 = this.mDate.split("/");
      if(split1.length == 1) {
         if(!isEndDate) {
            cal.set(2, 0);
            cal.set(5, 1);
         } else {
            cal.set(2, 11);
            cal.set(5, 31);
         }
      } else if(split1.length == 2) {
         if(!isEndDate) {
            cal.set(5, 1);
         } else {
            cal.add(2, 1);
            cal.add(5, -1);
         }
      }

      if(!split1[0].equals("yyyy")) {
         cal.set(1, Integer.valueOf(split1[0]).intValue());
      }

      if(split1.length > 1 && !split1[1].equals("mm")) {
         cal.set(2, Integer.valueOf(split1[1]).intValue());
      }

      if(split1.length > 2 && !split1[2].equals("dd")) {
         cal.set(2, Integer.valueOf(split1[2]).intValue());
      }

      cal.add(1, this.mYearOffset);
      cal.add(2, this.mMonthOffset);
      cal.add(5, this.mDayOffset);
      if(!isEndDate) {
         cal.set(11, 0);
         cal.set(12, 0);
         cal.set(13, 0);
         cal.set(14, 0);
      } else {
         cal.set(11, 23);
         cal.set(12, 59);
         cal.set(13, 59);
         cal.set(14, 999);
      }

      return new Timestamp(cal.getTimeInMillis());
   }

   public Timestamp getStartDate(Integer calSeId, CalendarInfo calInfo) {
      return this.getActualDate(calSeId, calInfo, false);
   }

   public Timestamp getEndDate(Integer calSeId, CalendarInfo calInfo) {
      return this.getActualDate(calSeId, calInfo, true);
   }
}
