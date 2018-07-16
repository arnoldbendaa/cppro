// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class NumberUtils {

   public static boolean isNumber(String s) {
      return s.matches("[+-]?[\\d,]+\\.?\\d*");
   }

   public static String removeFormattingFromString(String s) {
      return removeFromString(s, new String[]{",", "�", "$"});
   }

   public static String removeFromString(String s, String ... removal) {
      StringBuffer sb = new StringBuffer(s);
      boolean index = false;
      String[] arr$ = removal;
      int len$ = removal.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String remove = arr$[i$];

         int var8;
         while((var8 = sb.indexOf(remove)) > 0) {
            sb.deleteCharAt(var8);
         }
      }

      return sb.toString();
   }
}
