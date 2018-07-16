// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;

class RowHeaderBorder extends AbstractBorder {

   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Color oldColor = g.getColor();
      g.setColor(UIManager.getColor("Table.selectionBackground"));
      g.translate(x, y);
      g.drawLine(width - 1, 1, width - 1, height - 2);
      g.translate(-x, -y);
      g.setColor(oldColor);
   }

   public Insets getBorderInsets(Component c) {
      return new Insets(0, 1, 1, 1);
   }

   public Insets getBorderInsets(Component c, Insets insets) {
      insets.top = 0;
      insets.left = insets.right = insets.bottom = 1;
      return insets;
   }

   public boolean isBorderOpaque() {
      return true;
   }
}
