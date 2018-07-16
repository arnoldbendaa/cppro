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

public class HelpWindowBorder extends AbstractBorder {

   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Color oldColor = g.getColor();
      Color bgColor = c.getBackground();
      g.setColor(Color.black);
      g.drawRect(x, y, x + width - 4, y + height - 4);
      g.setColor(bgColor.darker());
      g.drawLine(x, y + height - 5, x + width, y + height - 5);
      g.drawLine(x, y + height - 4, x + width, y + height - 4);
      g.drawLine(x, y + height - 3, x + width, y + height - 3);
      g.drawLine(x, y + height - 2, x + width, y + height - 2);
      g.drawLine(x, y + height - 1, x + width, y + height - 1);
      g.drawLine(x, y + height - 0, x + width, y + height - 0);
      g.drawLine(x + width - 5, y, x + width - 5, y + height);
      g.drawLine(x + width - 4, y, x + width - 4, y + height);
      g.drawLine(x + width - 3, y, x + width - 3, y + height);
      g.drawLine(x + width - 2, y, x + width - 2, y + height);
      g.drawLine(x + width - 1, y, x + width - 1, y + height);
      g.drawLine(x + width - 0, y, x + width - 0, y + height);
      g.setColor(oldColor);
   }

   public Insets getBorderInsets(Component c) {
      return new Insets(1, 1, 6, 6);
   }

   public Insets getBorderInsets(Component c, Insets insets) {
      insets.left = insets.top = insets.right = insets.bottom = 1;
      return insets;
   }

   public boolean isBorderOpaque() {
      return true;
   }
}
