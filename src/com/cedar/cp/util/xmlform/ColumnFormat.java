// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.awt.ColorUtils;
import java.awt.Color;
import java.io.Serializable;

public class ColumnFormat implements Serializable {

   private int mAlignment = -1;
   private boolean mBlankWhenZero;
   private String mBackground;
   private Color mBackgroundColor;
   private String mPattern;
   public static final int LEFT = 0;
   public static final int CENTER = 1;
   public static final int RIGHT = 2;
   static String[] sAlignments = new String[]{"left", "center", "right"};


   public int getAlignment() {
      return this.mAlignment;
   }

   public void setAlignment(int alignment) {
      this.mAlignment = alignment;
   }

   public boolean isBlankWhenZero() {
      return this.mBlankWhenZero;
   }

   public void setBlankWhenZero(boolean blankWhenZero) {
      this.mBlankWhenZero = blankWhenZero;
   }

   public String getBackground() {
      return this.mBackground;
   }

   public void setBackground(String background) {
      this.mBackground = background;
      this.mBackgroundColor = null;
   }

   public Color getBackgroundColor() {
      if(this.mBackground == null) {
         return null;
      } else {
         if(this.mBackgroundColor == null) {
            this.mBackgroundColor = ColorUtils.getColorFromHexString(this.mBackground);
         }

         return this.mBackgroundColor;
      }
   }

   public String getPattern() {
      return this.mPattern;
   }

   public void setPattern(String pattern) {
      this.mPattern = pattern;
   }

}
