// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class SqlUtils {

   public static String generateTableName(String srcName, int maxLength) {
      StringBuffer result = new StringBuffer(srcName.toUpperCase());
      if(!isValidFirstChar(srcName.charAt(0))) {
         result.setCharAt(0, 'A');
      }

      for(int i = 1; i < result.length(); ++i) {
         if(!isValidTableChar(result.charAt(i))) {
            result.setCharAt(i, '_');
         }
      }

      int pos;
      while(result.length() > maxLength && (pos = result.toString().indexOf(95)) != -1) {
         result = result.delete(pos, pos + 1);
      }

      while(result.length() > maxLength && (pos = indexOfVowel(result.toString())) != -1) {
         result = result.delete(pos, pos + 1);
      }

      if(result.length() > maxLength) {
         result.delete(maxLength, result.length());
      }

      return result.toString();
   }

   private static int indexOfVowel(String str) {
      int length = str.length();
      int pos = -1;

      for(int i = 0; i < length && pos == -1; ++i) {
         pos = "AaEeIiOoUu".indexOf(str.charAt(i));
      }

      return pos;
   }

   private static boolean isValidFirstChar(char c) {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(Character.toUpperCase(c)) != -1;
   }

   private static boolean isValidTableChar(char c) {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$_#".indexOf(Character.toUpperCase(c)) != -1;
   }

   public static void main(String[] args) {
      String value = "$w.e.f.";
      System.out.println("Replace :" + value + " -> " + generateTableName(value, 27));
   }
}
