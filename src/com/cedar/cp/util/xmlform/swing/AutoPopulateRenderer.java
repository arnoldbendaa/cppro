// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.ImageLoader;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class AutoPopulateRenderer extends DefaultTableCellRenderer {

   private Color mProtectedColor = new Color(228, 244, 254);
   private int mStatus = 0;
   protected ImageIcon mNewIcon = ImageLoader.getImageIcon("new.gif");
   protected ImageIcon mDeleteIcon = ImageLoader.getImageIcon("delete.gif");


   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Color bgColor = this.mProtectedColor;
      this.setHorizontalAlignment(0);
      TableModel model = table.getModel();
      if(model instanceof FormTableModel) {
         FormTableModel ftModel = (FormTableModel)model;
         Color color = ftModel.getColumnBackgroundColor(column);
         if(color != null) {
            bgColor = color;
         }
      }

      this.setBackground(bgColor);
      return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
   }

   public void setValue(Object value) {
      this.mStatus = 0;
      if(value instanceof Number) {
         this.mStatus = ((Number)value).intValue();
      }

      super.setValue("");
   }

   public void paint(Graphics g) {
      super.paint(g);
      if(this.mStatus == 1) {
         this.centerIcon(g, this.mNewIcon);
      } else if(this.mStatus == -1) {
         this.centerIcon(g, this.mDeleteIcon);
      }

   }

   private void centerIcon(Graphics g, ImageIcon icon) {
      Rectangle r = g.getClipBounds();
      int x = (int)(r.getWidth() - 13.0D) / 2;
      int y = (int)(r.getHeight() - 12.0D) / 2;
      g.drawImage(icon.getImage(), x, y, (ImageObserver)null);
   }
}
