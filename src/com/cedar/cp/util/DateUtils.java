// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.Pair;
import com.cedar.cp.util.Timer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtils {

   public static String[][] sDATE_FORMATS = new String[][]{{"dd/MM/yy", "\\d{1,2}/\\d{1,2}/\\d{2}"}, {"dd/MMM/yy", "\\d{1,2}/[a-z]{3}/\\d{2}"}, {"dd/MM/yyyy", "\\d{1,2}/\\d{1,2}/\\d{4}"}, {"dd/MMM/yyyy", "\\d{1,2}/[a-z]{3}/\\d{4}"}, {"dd-MM-yy", "\\d{1,2}-\\d{1,2}-\\d{2}"}, {"dd-MMM-yy", "\\d{1,2}-[a-z]{3}-\\d{2}"}, {"dd-MM-yyyy", "\\d{1,2}-\\d{1,2}-\\d{4}"}, {"dd-MMM-yyyy", "\\d{1,2}-[a-z]{3}-\\d{4}"}, {"dd MM yy", "\\d{1,2}\\s\\d{1,2}\\s\\d{2}"}, {"dd MMM yy", "\\d{1,2}\\s[a-z]{3}\\s\\d{2}"}, {"dd MM yyyy", "\\d{1,2}\\s\\d{1,2}\\s\\d{4}"}, {"dd MMM yyyy", "\\d{1,2}\\s[a-z]{3}\\s\\d{4}"}, {"ddMMyyyy", "\\d{1,2}\\d{1,2}\\d{4}"}, {"ddMMMyyyy", "\\d{1,2}[a-z]{3}\\d{4}"}, {"MM/dd/yy", "\\d{1,2}/\\d{1,2}/\\d{2}"}, {"MMM/dd/yy", "[a-z]{3}/\\d{1,2}/\\d{2}"}, {"MM/dd/yyyy", "\\d{1,2}/\\d{1,2}/\\d{4}"}, {"MMM/dd/yyyy", "[a-z]{3}/\\d{1,2}/\\d{4}"}, {"MM-dd-yy", "\\d{1,2}-\\d{1,2}-\\d{2}"}, {"MMM-dd-yy", "[a-z]{3}-\\d{1,2}-\\d{2}"}, {"MM-dd-yyyy", "\\d{1,2}-\\d{1,2}-\\d{4}"}, {"MMM-dd-yyyy", "[a-z]{3}-\\d{1,2}-\\d{4}"}, {"MM dd yy", "\\d{1,2}\\s\\d{1,2}\\s\\d{2}"}, {"MMM dd yy", "[a-z]{3}\\s\\d{1,2}\\s\\d{2}"}, {"MM dd yyyy", "\\d{1,2}\\s\\d{1,2}\\s\\d{4}"}, {"MMM dd yyyy", "[a-z]{3}\\s\\d{1,2}\\s\\d{4}"}, {"MMddyyyy", "\\d{1,2}\\d{1,2}\\d{4}"}, {"MMMddyyyy", "[a-z]{3}\\d{1,2}\\d{4}"}};
   static List<Pair<String, Pattern>> sDatePatternList = new ArrayList();
   public static final String[][] sTIME_FORMAT;
   static List<Pair<String, Pattern>> sTimePatternList;
   static List<Pair<String, Pattern>> sDateTimePatternList;


   public static int daysBetweenDates(Date from, Date to) {
      double diff = (double)(from.getTime() - to.getTime());
      int intdiff = 1 + (int)(diff / 8.64E7D);
      return intdiff;
   }

   public static int diffBetweenDates(Date from, Date to) {
      boolean tempDifference = false;
      int difference = 0;
      Calendar earlier = Calendar.getInstance();
      Calendar later = Calendar.getInstance();
      if(from.compareTo(to) < 0) {
         earlier.setTime(from);
         later.setTime(to);
      } else {
         earlier.setTime(to);
         later.setTime(from);
      }

      int tempDifference1;
      while(earlier.get(1) != later.get(1)) {
         tempDifference1 = 365 * (later.get(1) - earlier.get(1));
         difference += tempDifference1;
         earlier.add(6, tempDifference1);
      }

      if(earlier.get(6) != later.get(6)) {
         tempDifference1 = later.get(6) - earlier.get(6);
         difference += tempDifference1;
         earlier.add(6, tempDifference1);
      }

      return difference;
   }

   public static Date parseDate(SimpleDateFormat sdf, String dateString) {
      if(dateString == null) {
         return null;
      } else {
         String workString = dateString.toLowerCase();
         Iterator i$ = sDatePatternList.iterator();

         while(true) {
            Pair entry;
            do {
               if(!i$.hasNext()) {
                  return null;
               }

               entry = (Pair)i$.next();
            } while(!((Pattern)entry.getChild2()).matcher(workString).matches());

            try {
               sdf.applyPattern((String)entry.getChild1());
               return sdf.parse(dateString);
            } catch (ParseException var6) {
               ;
            }
         }
      }
   }

   public static Date parseTime(SimpleDateFormat sdf, String timeString) {
      if(timeString == null) {
         return null;
      } else {
         String workTimeString = timeString.toLowerCase();
         Iterator i$ = sTimePatternList.iterator();

         while(true) {
            Pair entry;
            do {
               if(!i$.hasNext()) {
                  return null;
               }

               entry = (Pair)i$.next();
            } while(!((Pattern)entry.getChild2()).matcher(workTimeString).matches());

            try {
               sdf.applyPattern((String)entry.getChild1());
               return sdf.parse(timeString);
            } catch (ParseException var6) {
               ;
            }
         }
      }
   }

   public static Date parseDateTime(SimpleDateFormat sdf, String dateTimeString) {
      if(dateTimeString == null) {
         return null;
      } else {
         String workDateTimeString = dateTimeString.toLowerCase();
         Iterator timeResult = sDateTimePatternList.iterator();

         while(true) {
            Pair entry;
            do {
               if(!timeResult.hasNext()) {
                  Date timeResult1 = parseTime(sdf, dateTimeString);
                  if(timeResult1 != null) {
                     return timeResult1;
                  }

                  return parseDate(sdf, dateTimeString);
               }

               entry = (Pair)timeResult.next();
            } while(!((Pattern)entry.getChild2()).matcher(workDateTimeString).matches());

            try {
               sdf.applyPattern((String)entry.getChild1());
               return sdf.parse(dateTimeString);
            } catch (ParseException var6) {
               ;
            }
         }
      }
   }

   private static void testParseDateTime(SimpleDateFormat sdf, String value) {
      System.out.println("DateTime [" + value + "] parsed into [" + parseDateTime(sdf, value) + "]");
   }

   private static void testParseDate(SimpleDateFormat sdf, String value) {
      System.out.println("Date [" + value + "] parsed into [" + parseDate(sdf, value) + "]");
   }

   private static void testParseTime(SimpleDateFormat sdf, String value) {
      System.out.println("Time [" + value + "] parsed into [" + parseTime(sdf, value) + "]");
   }

   public static void main(String[] args) throws Exception {
      new Timer();
      SimpleDateFormat sdf = new SimpleDateFormat();
      System.out.println(Locale.getDefault());
      testParseDateTime(sdf, "12/34/1999");
   }

   public static int compareDates(String dateOneText, String dateTwoText, String format) {
      try {
         SimpleDateFormat e = new SimpleDateFormat(format);
         Date dateOne = e.parse(dateOneText);
         Date dateTwo = e.parse(dateTwoText);
         return dateOne.compareTo(dateTwo);
      } catch (ParseException var6) {
         System.err.println("compareDates:Failed to parse dates");
         return Integer.MAX_VALUE;
      }
   }

   static int getDaysInMonth(String date, String format) {
      try {
         SimpleDateFormat e = new SimpleDateFormat(format);
         return getDaysInMonth(e.parse(date));
      } catch (ParseException var3) {
         return -1;
      }
   }

   static int getDaysInMonth(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.getActualMaximum(5);
   }

   static int getDayOfMonth(String date, String format) {
      try {
         SimpleDateFormat e = new SimpleDateFormat(format);
         return getDayOfMonth(e.parse(date));
      } catch (ParseException var3) {
         return -1;
      }
   }

   static int getDayOfMonth(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      return cal.get(5);
   }

   static {
      String[][] arr$ = sDATE_FORMATS;
      int len$ = arr$.length;

      int i$;
      String[] d;
      for(i$ = 0; i$ < len$; ++i$) {
         d = arr$[i$];
         sDatePatternList.add(new Pair(d[0], Pattern.compile(d[1])));
      }

      sTIME_FORMAT = new String[][]{{"HH:mm:ss", "\\d{1,2}:\\d{1,2}:\\d{1,2}"}, {"HH mm ss", "\\d{1,2}\\s\\d{1,2}\\s\\d{1,2}"}, {"HHmmss", "\\d{1,2}\\d{1,2}\\d{1,2}"}, {"HH:mm", "\\d{1,2}:\\d{1,2}"}, {"HH mm", "\\d{1,2}\\s\\d{1,2}"}, {"HHmm", "\\d{1,2}\\d{1,2}"}};
      sTimePatternList = new ArrayList();
      arr$ = sTIME_FORMAT;
      len$ = arr$.length;

      for(i$ = 0; i$ < len$; ++i$) {
         d = arr$[i$];
         sTimePatternList.add(new Pair(d[0], Pattern.compile(d[1])));
      }

      sDateTimePatternList = new ArrayList();
      arr$ = sDATE_FORMATS;
      len$ = arr$.length;

      for(i$ = 0; i$ < len$; ++i$) {
         d = arr$[i$];
         String[][] arr$1 = sTIME_FORMAT;
         int len$1 = arr$1.length;

         int i$1;
         String[] t;
         for(i$1 = 0; i$1 < len$1; ++i$1) {
            t = arr$1[i$1];
            sDateTimePatternList.add(new Pair(d[0] + t[0], Pattern.compile(d[1] + t[1])));
         }

         arr$1 = sTIME_FORMAT;
         len$1 = arr$1.length;

         for(i$1 = 0; i$1 < len$1; ++i$1) {
            t = arr$1[i$1];
            sDateTimePatternList.add(new Pair(d[0] + ' ' + t[0], Pattern.compile(d[1] + "\\s" + t[1])));
         }

         arr$1 = sTIME_FORMAT;
         len$1 = arr$1.length;

         for(i$1 = 0; i$1 < len$1; ++i$1) {
            t = arr$1[i$1];
            sDateTimePatternList.add(new Pair(t[0] + d[0], Pattern.compile(t[1] + d[1])));
         }

         arr$1 = sTIME_FORMAT;
         len$1 = arr$1.length;

         for(i$1 = 0; i$1 < len$1; ++i$1) {
            t = arr$1[i$1];
            sDateTimePatternList.add(new Pair(t[0] + ' ' + d[0], Pattern.compile(t[1] + "\\s" + d[1])));
         }
      }

   }
}
