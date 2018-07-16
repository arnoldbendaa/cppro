// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

class CalcButtonBorder extends BevelBorder {

   protected static Insets borderInsets = new Insets(0, 0, 0, 0);


   public CalcButtonBorder() {
      super(0);
   }

   public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      if(!(c instanceof AbstractButton)) {
         super.paintBorder(c, g, x, y, w, h);
      } else {
         AbstractButton button = (AbstractButton)c;
         ButtonModel model = button.getModel();
         if(model.isEnabled()) {
            boolean isPressed = model.isPressed() && model.isArmed();
            boolean isDefault = button instanceof JButton && ((JButton)button).isDefaultButton();
            if(isPressed && isDefault) {
               g.setColor(Color.BLACK);
               g.drawLine(0, 0, 0, h - 1);
               g.drawLine(0, 0, w - 1, 0);
               g.setColor(Color.BLACK);
               g.drawLine(1, 1, 1, h - 3);
               g.drawLine(2, 1, w - 3, 1);
               g.setColor(Color.LIGHT_GRAY);
               g.drawLine(1, h - 2, w - 2, h - 2);
               g.drawLine(w - 2, 1, w - 2, h - 3);
               g.setColor(Color.WHITE);
               g.drawLine(0, h - 1, w - 1, h - 1);
               g.drawLine(w - 1, h - 1, w - 1, 0);
            } else if(isPressed) {
               g.setColor(Color.BLACK);
               g.drawLine(0, 0, 0, h - 1);
               g.drawLine(0, 0, w - 1, 0);
               g.setColor(Color.DARK_GRAY);
               g.drawLine(1, 1, 1, h - 3);
               g.drawLine(2, 1, w - 3, 1);
               g.setColor(Color.LIGHT_GRAY);
               g.drawLine(1, h - 2, w - 2, h - 2);
               g.drawLine(w - 2, 1, w - 2, h - 3);
               g.setColor(Color.WHITE);
               g.drawLine(0, h - 1, w - 1, h - 1);
               g.drawLine(w - 1, h - 1, w - 1, 0);
            } else if(isDefault) {
               g.setColor(Color.WHITE);
               g.drawLine(0, 0, 0, h - 1);
               g.drawLine(0, 0, w - 1, 0);
               g.setColor(Color.LIGHT_GRAY);
               g.drawLine(1, 1, 1, h - 3);
               g.drawLine(2, 1, w - 3, 1);
               g.setColor(Color.DARK_GRAY);
               g.drawLine(1, h - 2, w - 2, h - 2);
               g.drawLine(w - 2, 1, w - 2, h - 3);
               g.setColor(Color.BLACK);
               g.drawLine(0, h - 1, w - 1, h - 1);
               g.drawLine(w - 1, h - 1, w - 1, 0);
            } else {
               g.setColor(Color.WHITE);
               g.drawLine(0, 0, 0, h - 1);
               g.drawLine(0, 0, w - 1, 0);
               g.setColor(Color.LIGHT_GRAY);
               g.drawLine(1, 1, 1, h - 3);
               g.drawLine(2, 1, w - 3, 1);
               g.setColor(Color.BLACK);
               g.drawLine(1, h - 2, w - 2, h - 2);
               g.drawLine(w - 2, 1, w - 2, h - 3);
               g.setColor(Color.BLACK);
               g.drawLine(0, h - 1, w - 1, h - 1);
               g.drawLine(w - 1, h - 1, w - 1, 0);
            }
         } else {
            g.setColor(Color.WHITE);
            g.drawLine(0, 0, 0, h - 1);
            g.drawLine(0, 0, w - 1, 0);
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(1, 1, 1, h - 3);
            g.drawLine(2, 1, w - 3, 1);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(1, h - 2, w - 2, h - 2);
            g.drawLine(w - 2, 1, w - 2, h - 3);
            g.setColor(Color.BLACK);
            g.drawLine(0, h - 1, w - 1, h - 1);
            g.drawLine(w - 1, h - 1, w - 1, 0);
         }

      }
   }

   public Insets getBorderInsets(Component c) {
      return borderInsets;
   }

   public Insets getBorderInsets(Component c, Insets newInsets) {
      newInsets.top = borderInsets.top;
      newInsets.left = borderInsets.left;
      newInsets.bottom = borderInsets.bottom;
      newInsets.right = borderInsets.right;
      return newInsets;
   }

}
