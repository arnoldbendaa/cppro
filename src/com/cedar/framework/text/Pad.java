// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.text;


public class Pad {

   public static final char SPACE = ' ';


   public static String withChar(String s, char c, int toLength) {
      if(toLength <= 0) {
         return s;
      } else if(s != null && s.length() >= toLength) {
         return s;
      } else {
         boolean numberToAdd = false;
         String result = "";
         int var7;
         if(s == null) {
            var7 = toLength;
         } else {
            var7 = toLength - s.length();
            result = s;
         }

         StringBuffer additionalString = new StringBuffer();

         for(int i = 0; i < var7; ++i) {
            additionalString.append(c);
         }

         result = result + additionalString.toString();
         return result;
      }
   }

   public static String withBlanks(String s, int toLength) {
      return withChar(s, ' ', toLength);
   }
}
