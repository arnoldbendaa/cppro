// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

public class NumberFormatter {

   private static DecimalFormat sDecimalFormatter = null;
   private static NumberFormat sNumberFormatter = null;
   private static Long sLongZero = new Long(0L);


   public static synchronized String format(long value, int scale) {
      DecimalFormat f = getDecimalFormatter();
      f.setMinimumFractionDigits(scale);
      f.setMaximumFractionDigits(scale);
      f.setGroupingUsed(true);
      double interim = (double)value / Math.pow(10.0D, (double)scale);
      return f.format(interim);
   }

   public static synchronized String format(long value, int scale, boolean useGrouping) {
      DecimalFormat f = getDecimalFormatter();
      f.setMinimumFractionDigits(scale);
      f.setMaximumFractionDigits(scale);
      f.setGroupingUsed(useGrouping);
      double interim = (double)value / Math.pow(10.0D, (double)scale);
      return f.format(interim);
   }

   public static synchronized Long parse(String text, int scale) throws ParseException {
      Long value = null;
      NumberFormat f = getNumberFormatter();
      f.setParseIntegerOnly(false);
      ParsePosition p = new ParsePosition(0);
      Number n = f.parse(text, p);
      if(p.getErrorIndex() != -1) {
         throw new ParseException("Invalid number format", p.getErrorIndex());
      } else if(p.getIndex() != text.length()) {
         throw new ParseException("Invalid characters found", p.getIndex());
      } else {
         double dValue;
         if(n instanceof Long) {
            dValue = (double)((Long)n).longValue() * Math.pow(10.0D, (double)scale);
         } else {
            dValue = ((Double)n).doubleValue() * Math.pow(10.0D, (double)scale);
         }

         value = new Long(Math.round(Math.rint(dValue)));
         return value;
      }
   }

   public static synchronized String format(double value) {
      return format(value, 2, 2);
   }

   public static synchronized String format(double value, int minDigits, int maxDigits) {
      DecimalFormat f = getDecimalFormatter();
      f.setMinimumFractionDigits(minDigits);
      f.setMaximumFractionDigits(maxDigits);
      return f.format(value);
   }

   public static synchronized String format(long value) {
      NumberFormat f = getNumberFormatter();
      return f.format(value);
   }

   public static synchronized String format(long value, boolean useGrouping) {
      NumberFormat f = getNumberFormatter();
      f.setGroupingUsed(useGrouping);
      return f.format(value);
   }

   public static synchronized double parseDouble(String text) throws ParseException {
      double value = 0.0D;
      DecimalFormat f = getDecimalFormatter();
      ParsePosition p = new ParsePosition(0);
      Number n = f.parse(text, p);
      if(p.getErrorIndex() != -1) {
         throw new ParseException("Invalid number format", p.getErrorIndex());
      } else {
         value = n.doubleValue();
         return value;
      }
   }

   public static synchronized Long parse(String text) throws ParseException {
      return parse(text, true);
   }

   public static synchronized Long parse(String text, boolean checkLength) throws ParseException {
      Long value = null;
      NumberFormat f = getNumberFormatter();
      f.setParseIntegerOnly(true);
      ParsePosition p = new ParsePosition(0);
      value = (Long)f.parse(text, p);
      if(p.getErrorIndex() != -1) {
         throw new ParseException("Invalid number format", p.getErrorIndex());
      } else if(checkLength && p.getIndex() != text.length()) {
         throw new ParseException("Invalid characters found", p.getIndex());
      } else {
         return value;
      }
   }

   public static String getColorHexValue(Color color) {
      int rgb = color.getRed() * 256 * 256 + color.getGreen() * 256 + color.getBlue();
      String s = Integer.toHexString(rgb);
      if(s.length() < 6) {
         s = "000000" + s;
         int start = s.length() - 6;
         s = s.substring(start);
      }

      return s;
   }

   public static int parseHexColor(String hexValue) throws NumberFormatException {
      return Integer.parseInt(hexValue, 16);
   }

   public static int[] rgbValuesOfColor(int color) {
      int red = color >>> 16 & 255;
      int green = color >>> 8 & 255;
      int blue = color & 255;
      return new int[]{red, green, blue};
   }

   private static DecimalFormat getDecimalFormatter() {
      if(sDecimalFormatter == null) {
         sDecimalFormatter = new DecimalFormat();
      }

      return sDecimalFormatter;
   }

   private static NumberFormat getNumberFormatter() {
      if(sNumberFormatter == null) {
         sNumberFormatter = NumberFormat.getInstance();
      }

      return sNumberFormatter;
   }

}
