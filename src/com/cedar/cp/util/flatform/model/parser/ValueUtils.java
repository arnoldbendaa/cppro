// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.DateUtils;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ValueUtils {

   private static SimpleDateFormat mDateFormat = new SimpleDateFormat();
   private static DecimalFormat sInputNumberFormat = new DecimalFormat("#,##0.00;-#,##0.00");


   public static boolean isNumberChars(String s) {
      if(s != null && s.trim().length() != 0) {
         for(int i = 0; i < s.trim().length(); ++i) {
            char c = s.charAt(i);
            if(!Character.isDigit(c) && !isNumberFormatChar(c) && !Character.isWhitespace(c)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static double getDoubleValue(String s) throws ParseException {
      return sInputNumberFormat.parse(s).doubleValue();
   }

   private static boolean isNumberFormatChar(char c) {
      return c == 46 || c == 43 || c == 45 || c == 44 || c == 40 || c == 41;
   }

   public static Date convert2Date(String value, boolean lenient) {
      Date result = null;
      mDateFormat.setLenient(lenient);
      result = DateUtils.parseDateTime(mDateFormat, value);
      if(result == null) {
         try {
            Integer.parseInt(value);
            result = convert2Date(Integer.parseInt(value));
         } catch (NumberFormatException var4) {
            ;
         }
      }

      return result;
   }

   public static Date convert2Date(int value) {
      Date result = null;
      Calendar c = Calendar.getInstance();
      c.set(1900, 0, 1);
      if(value != 0) {
         c.add(6, value - 2);
      }

      result = c.getTime();
      return result;
   }

   public static Object getPercentValue(String value) {
      String result = null;
      if(isEmpty(value)) {
         return result;
      } else {
         char c = value.charAt(value.length() - 1);
         if(c == 37) {
            result = value.substring(0, value.length() - 1);
         } else {
            result = value;
         }

         Double result1;
         try {
            result1 = Double.valueOf(Double.parseDouble((String)result));
         } catch (NumberFormatException var4) {
            result1 = null;
         }

         if(result1 != null && c == 37) {
            result1 = Double.valueOf(((Number)result1).doubleValue() / 100.0D);
         }

         return result1;
      }
   }

   private static boolean isEmpty(String value) {
      boolean result = false;
      if(value == null || value.trim().isEmpty()) {
         result = true;
      }

      return result;
   }

}
