// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

public class TotalBorder extends AbstractBorder {

   protected Color lineColor;


   public TotalBorder(Color color) {
      this.lineColor = color;
   }

   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Color oldColor = g.getColor();
      g.setColor(this.lineColor);
      g.drawLine(x, 0, width, 0);
      g.drawLine(x, height - 2, width, height - 2);
      g.drawLine(x, height, width, height);
      g.setColor(oldColor);
   }

   public Insets getBorderInsets(Component c) {
      return new Insets(1, 0, 3, 0);
   }

   public Insets getBorderInsets(Component c, Insets insets) {
      insets.left = insets.right = 0;
      insets.top = 1;
      insets.bottom = 3;
      return insets;
   }

   public Color getLineColor() {
      return this.lineColor;
   }

   public boolean isBorderOpaque() {
      return true;
   }
}
