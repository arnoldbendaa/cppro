// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.text;


public class EnsureLength {

   public static String ensureLength(String string, int maxSize) {
      if(string == null) {
         return null;
      } else {
         string = string.trim();
         string = string.substring(0, Math.min(maxSize, string.length()));
         return string.trim();
      }
   }
}
