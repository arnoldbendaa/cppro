// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;

public class ColorUtils {

   public static Color getColorFromHexString(String hexColor) {
      Color color = null;
      if(hexColor != null) {
         try {
            color = Color.decode(hexColor);
         } catch (NumberFormatException var5) {
            hexColor = "0x" + hexColor;

            try {
               color = Color.decode(hexColor);
            } catch (NumberFormatException var4) {
               return null;
            }
         }
      }

      return color;
   }

   public static String getHexStringFromColor(Color color) {
      StringBuilder hexColor = new StringBuilder();
      String red = Integer.toHexString(color.getRed());
      if(red.length() < 2) {
         hexColor.append("0");
      }

      hexColor.append(red);
      String green = Integer.toHexString(color.getGreen());
      if(green.length() < 2) {
         hexColor.append("0");
      }

      hexColor.append(green);
      String blue = Integer.toHexString(color.getBlue());
      if(blue.length() < 2) {
         hexColor.append("0");
      }

      hexColor.append(blue);
      return hexColor.toString();
   }
}
