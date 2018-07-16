// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class ColorRenderer extends JLabel implements TableCellRenderer {

   Border unselectedBorder = null;
   Border selectedBorder = null;
   boolean isBordered = true;


   public ColorRenderer(boolean isBordered) {
      this.isBordered = isBordered;
      this.setOpaque(true);
   }

   public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
      if(color == null) {
         this.setText("Not set");
         this.setBackground(Color.white);
      } else {
         this.setText("");
         Color newColor = (Color)color;
         this.setBackground(newColor);
         if(this.isBordered) {
            if(isSelected) {
               if(this.selectedBorder == null) {
                  this.selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
               }

               this.setBorder(this.selectedBorder);
            } else {
               if(this.unselectedBorder == null) {
                  this.unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
               }

               this.setBorder(this.unselectedBorder);
            }
         }

         this.setToolTipText("RGB value: " + newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue());
      }

      return this;
   }
}
