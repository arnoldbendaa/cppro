// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class ColoredIcon implements Icon {

   private int mWidth;
   private int mHeight;
   private Color mBackground;


   public ColoredIcon(int width, int height) {
      this.mWidth = width;
      this.mHeight = height;
   }

   public ColoredIcon() {
      this(9, 9);
   }

   public void paintIcon(Component c, Graphics g, int x, int y) {
      Color bgColor = this.mBackground;
      if(bgColor == null) {
         bgColor = c.getBackground();
      }

      if(bgColor != null) {
         g.setColor(bgColor);
      } else {
         g.setColor(Color.white);
      }

      g.fillRect(x, y, this.mWidth - 1, this.mHeight - 1);
   }

   public int getIconWidth() {
      return this.mWidth;
   }

   public int getIconHeight() {
      return this.mHeight;
   }

   public void setColor(Color color) {
      this.mBackground = color;
   }

   public Color getColor() {
      return this.mBackground;
   }
}
