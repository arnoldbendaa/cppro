// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

public class RolloverBorder extends AbstractBorder {

   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Color oldColor = g.getColor();
      Color bgColor = c.getBackground();
      g.setColor(bgColor.brighter());
      g.drawLine(x, y, x + width - 1, y);
      g.drawLine(x, y, x, y + height - 1);
      g.setColor(bgColor.darker());
      g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
      g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
      g.setColor(oldColor);
   }

   public Insets getBorderInsets(Component c) {
      return new Insets(1, 1, 1, 1);
   }

   public Insets getBorderInsets(Component c, Insets insets) {
      insets.left = insets.top = insets.right = insets.bottom = 1;
      return insets;
   }

   public boolean isBorderOpaque() {
      return true;
   }
}
