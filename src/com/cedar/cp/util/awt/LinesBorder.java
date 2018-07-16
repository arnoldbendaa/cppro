// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.ColorUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class LinesBorder implements Border, SwingConstants, Serializable {

   public static final int sNO_BORDER_VALUE_TYPE = 0;
   public static final int sDOUBLE_BORDER_VALUE_TYPE = -1;
   protected int northThickness;
   protected int southThickness;
   protected int eastThickness;
   protected int westThickness;
   protected Color northColor;
   protected Color southColor;
   protected Color eastColor;
   protected Color westColor;


   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(!(obj instanceof LinesBorder)) {
         return super.equals(obj);
      } else {
         LinesBorder other = (LinesBorder)obj;
         return this.northThickness == other.northThickness && (this.northColor == other.northColor || this.northColor != null && other.northColor != null && this.northColor.equals(other.northColor)) && this.southThickness == other.southThickness && (this.southColor == other.southColor || this.southColor != null && other.southColor != null && this.southColor.equals(other.southColor)) && this.eastThickness == other.eastThickness && (this.eastColor == other.eastColor || this.eastColor != null && other.eastColor != null && this.eastColor.equals(other.eastColor)) && this.westThickness == other.westThickness && (this.westColor == other.westColor || this.westColor != null && other.westColor != null && this.westColor.equals(other.westColor));
      }
   }

   public int hashCode() {
      return this.northThickness + this.southThickness + this.eastThickness + this.westThickness;
   }

   public LinesBorder() {
      this(Color.black, 0);
   }

   public LinesBorder(Color color) {
      this(color, 1);
   }

   public LinesBorder(Color color, int thickness) {
      this.setColor(color);
      this.setThickness(thickness);
   }

   public LinesBorder(Color color, Insets insets) {
      this(color, 1);
      this.setThickness(insets);
   }

   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Color oldColor = g.getColor();
      g.setColor(this.northColor);
      int i;
      if(this.northThickness == -1) {
         g.drawLine(x, y + 0, x + width - 1, y + 0);
         g.drawLine(x, y + 2, x + width - 1, y + 2);
      } else {
         for(i = 0; i < this.northThickness; ++i) {
            g.drawLine(x, y + i, x + width - 1, y + i);
         }
      }

      g.setColor(this.southColor);
      if(this.southThickness == -1) {
         g.drawLine(x, y + height - 0 - 1, x + width - 1, y + height - 0 - 1);
         g.drawLine(x, y + height - 2 - 1, x + width - 1, y + height - 2 - 1);
      } else {
         for(i = 0; i < this.southThickness; ++i) {
            g.drawLine(x, y + height - i - 1, x + width - 1, y + height - i - 1);
         }
      }

      g.setColor(this.westColor);

      for(i = 0; i < this.westThickness; ++i) {
         g.drawLine(x + i, y, x + i, y + height - 1);
      }

      g.setColor(this.eastColor);

      for(i = 0; i < this.eastThickness; ++i) {
         g.drawLine(x + width - i - 1, y, x + width - i - 1, y + height - 1);
      }

      g.setColor(oldColor);
   }

   public Insets getBorderInsets(Component c) {
      return new Insets(this.northThickness == -1?3:this.northThickness, this.westThickness, this.southThickness == -1?3:this.southThickness, this.eastThickness);
   }

   public Insets getBorderInsets(Component c, Insets insets) {
      return new Insets(this.northThickness == -1?3:this.northThickness, this.westThickness, this.southThickness == -1?3:this.southThickness, this.eastThickness);
   }

   public boolean isBorderOpaque() {
      return true;
   }

   public void setColor(Color c) {
      this.northColor = c;
      this.southColor = c;
      this.eastColor = c;
      this.westColor = c;
   }

   public void setColor(Color top, Color left, Color bottom, Color right) {
      this.northColor = top;
      this.westColor = left;
      this.southColor = bottom;
      this.eastColor = right;
   }

   public void setColor(Color c, int direction) {
      switch(direction) {
      case 1:
         this.northColor = c;
      case 2:
      case 4:
      case 6:
      default:
         break;
      case 3:
         this.eastColor = c;
         break;
      case 5:
         this.southColor = c;
         break;
      case 7:
         this.westColor = c;
      }

   }

   public void setThickness(int n) {
      this.northThickness = n;
      this.southThickness = n;
      this.eastThickness = n;
      this.westThickness = n;
   }

   public void setThickness(Insets insets) {
      this.northThickness = insets.top;
      this.southThickness = insets.bottom;
      this.eastThickness = insets.right;
      this.westThickness = insets.left;
   }

   public void setThickness(int top, int left, int bottom, int right) {
      this.northThickness = top;
      this.westThickness = left;
      this.southThickness = bottom;
      this.eastThickness = right;
   }

   public void setThickness(int n, int direction) {
      switch(direction) {
      case 1:
         this.northThickness = n;
      case 2:
      case 4:
      case 6:
      default:
         break;
      case 3:
         this.eastThickness = n;
         break;
      case 5:
         this.southThickness = n;
         break;
      case 7:
         this.westThickness = n;
      }

   }

   public void setNorthThickness(int northThickness) {
      this.northThickness = northThickness;
   }

   public void setSouthThickness(int southThickness) {
      this.southThickness = southThickness;
   }

   public void setEastThickness(int eastThickness) {
      this.eastThickness = eastThickness;
   }

   public void setWestThickness(int westThickness) {
      this.westThickness = westThickness;
   }

   public void setNorthColor(String northColor) {
      this.northColor = ColorUtils.getColorFromHexString(northColor);
   }

   public void setSouthColor(String southColor) {
      this.southColor = ColorUtils.getColorFromHexString(southColor);
   }

   public void setEastColor(String eastColor) {
      this.eastColor = ColorUtils.getColorFromHexString(eastColor);
   }

   public void setWestColor(String westColor) {
      this.westColor = ColorUtils.getColorFromHexString(westColor);
   }

   public int getNorthThickness() {
      return this.northThickness;
   }

   public int getSouthThickness() {
      return this.southThickness;
   }

   public int getEastThickness() {
      return this.eastThickness;
   }

   public int getWestThickness() {
      return this.westThickness;
   }

   public Color getNorthColor() {
      return this.northColor;
   }

   public Color getSouthColor() {
      return this.southColor;
   }

   public Color getEastColor() {
      return this.eastColor;
   }

   public Color getWestColor() {
      return this.westColor;
   }

   public void append(LinesBorder b, boolean isReplace) {
      if(isReplace) {
         this.northThickness = b.northThickness;
         this.southThickness = b.southThickness;
         this.eastThickness = b.eastThickness;
         this.westThickness = b.westThickness;
      } else {
         this.northThickness = Math.max(this.northThickness, b.northThickness);
         this.southThickness = Math.max(this.southThickness, b.southThickness);
         this.eastThickness = Math.max(this.eastThickness, b.eastThickness);
         this.westThickness = Math.max(this.westThickness, b.westThickness);
      }

   }

   public void append(Insets insets, boolean isReplace) {
      if(isReplace) {
         this.northThickness = insets.top;
         this.southThickness = insets.bottom;
         this.eastThickness = insets.right;
         this.westThickness = insets.left;
      } else {
         this.northThickness = Math.max(this.northThickness, insets.top);
         this.southThickness = Math.max(this.southThickness, insets.bottom);
         this.eastThickness = Math.max(this.eastThickness, insets.right);
         this.westThickness = Math.max(this.westThickness, insets.left);
      }

   }
}
