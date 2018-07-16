// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

public class GeneralUtils {

   private static char sDefaultSeparator = 47;
   private static int sMaxLength = 1000000;


   public static String listToString(List l) {
      return listToString(l, sDefaultSeparator, sMaxLength);
   }

   public static String listToString(List l, int maxLen) {
      return listToString(l, sDefaultSeparator, maxLen);
   }

   public static String listToString(List l, char separator) {
      return listToString(l, separator, sMaxLength);
   }

   public static String listToString(List l, char separator, int maxLen) {
      StringBuffer params = new StringBuffer(256);
      Iterator iter = l.iterator();

      while(iter.hasNext()) {
         if(params.length() > 0) {
            params.append(separator);
         }

         params.append((String)iter.next());
         if(params.length() > maxLen) {
            params.setLength(maxLen);
            break;
         }
      }

      return params.toString();
   }

   public static boolean safeEqual(Object o1, Object o2) {
      return o1 == null && o2 == null || o1 != null && o2 != null && o1.equals(o2);
   }

   public static boolean deepSafeEquals(int[] o1, int[] o2) {
      if(o1 == null && o2 == null) {
         return true;
      } else {
         if(o1 != null && o2 != null) {
            if(o1.length != o2.length) {
               return false;
            }

            for(int i = 0; i < o1.length; ++i) {
               if(o1[i] != o2[i]) {
                  return false;
               }
            }
         }

         return false;
      }
   }

   public static boolean isDifferent(Object o1, Object o2) {
      return o1 != null && o2 == null || o1 == null && o2 != null || o1 != null && o2 != null && !o1.equals(o2);
   }

   public static boolean isEmptyOrNull(String s) {
      return s == null || s.trim().length() == 0;
   }

   public static long convertFinancialValueToDB(double value) {
      return (new BigDecimal(value)).setScale(2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(10000L)).longValue();
   }

   public static double convertDBToFinancialValue(long value) {
      return (double)value / 10000.0D;
   }

   public static boolean contains(int target, int[] elements) {
      for(int i = 0; i < elements.length; ++i) {
         if(elements[i] == target) {
            return true;
         }
      }

      return false;
   }

}
