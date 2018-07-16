// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorGradient {

   private Map<Color, Color[]> mColorGradients = new HashMap();
   private int mGradientSize;


   public ColorGradient(int size) {
      this.mGradientSize = size;
   }

   public Color[] getColorGradients(Color bgColor) {
      Color[] colors = (Color[])this.mColorGradients.get(bgColor);
      if(colors == null) {
         colors = this.getColorGradients(bgColor, this.mGradientSize);
         this.mColorGradients.put(bgColor, colors);
      }

      return colors;
   }

   public Color getColorAtDepth(Color bgColor, int depth) {
      Color[] gradients = this.getColorGradients(bgColor);
      return depth < 0?gradients[0]:(depth >= gradients.length?gradients[gradients.length - 1]:gradients[depth]);
   }

   private Color[] getColorGradients(Color bgColor, int size) {
      Color[] colors = new Color[size];
      colors = new Color[size];
      int red = bgColor.getRed();
      int rInc = (255 - red) / size;
      int green = bgColor.getGreen();
      int gInc = (255 - green) / size;
      int blue = bgColor.getBlue();
      int bInc = (255 - blue) / size;

      for(int i = 0; i < size; ++i) {
         colors[i] = new Color(red, green, blue);
         red += rInc;
         green += gInc;
         blue += bInc;
         if(red > 255) {
            red = 255;
         }

         if(green > 255) {
            green = 255;
         }

         if(blue > 255) {
            blue = 255;
         }
      }

      return colors;
   }
}
