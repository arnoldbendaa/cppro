// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:37:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.framework.text;

import java.util.ArrayList;

public class FrameworkTextWrapper {

   public static String[] wrapText(int length, String inText) {
      ArrayList wrappedText = new ArrayList();
      boolean notFinished = true;
      String text = new String(inText);
      --length;

      do {
         if(text.length() <= length) {
            wrappedText.add(text);
            notFinished = false;
            break;
         }

         int array = findWrapPoint(length, text);
         wrappedText.add(text.substring(0, array));
         text = text.substring(array + 1);
      } while(notFinished);

      String[] var6 = new String[wrappedText.size()];
      wrappedText.toArray(var6);
      return var6;
   }

   private static int findWrapPoint(int length, String text) {
      int wrapPoint = length;
      String start = text.substring(0, length);
      if(isWrapPoint(start.charAt(start.length() - 1))) {
         wrapPoint = start.length() - 1;
      } else {
         for(int i = length; i > 0; --i) {
            if(isWrapPoint(start.charAt(i - 1))) {
               wrapPoint = i - 1;
               break;
            }
         }
      }

      return wrapPoint;
   }

   private static boolean isWrapPoint(char character) {
      boolean rVal = false;
      switch(character) {
      case 32:
         rVal = true;
         break;
      case 33:
         rVal = true;
         break;
      case 44:
         rVal = true;
         break;
      case 46:
         rVal = true;
         break;
      case 58:
         rVal = true;
         break;
      case 59:
         rVal = true;
         break;
      case 63:
         rVal = true;
      }

      return rVal;
   }
}
