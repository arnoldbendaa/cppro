// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class TextAreaTableCellRenderer extends JTextArea implements TableCellRenderer, Serializable {

   protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
   private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
   private Color unselectedForeground;
   private Color unselectedBackground;


   public TextAreaTableCellRenderer() {
      this.setOpaque(true);
      this.setBorder(getNoFocusBorder());
   }

   private static Border getNoFocusBorder() {
      return System.getSecurityManager() != null?SAFE_NO_FOCUS_BORDER:noFocusBorder;
   }

   public void setForeground(Color c) {
      super.setForeground(c);
      this.unselectedForeground = c;
   }

   public void setBackground(Color c) {
      super.setBackground(c);
      this.unselectedBackground = c;
   }

   public void updateUI() {
      super.updateUI();
      this.setForeground((Color)null);
      this.setBackground((Color)null);
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(isSelected) {
         super.setForeground(table.getSelectionForeground());
         super.setBackground(table.getSelectionBackground());
      } else {
         super.setForeground(this.unselectedForeground != null?this.unselectedForeground:table.getForeground());
         super.setBackground(this.unselectedBackground != null?this.unselectedBackground:table.getBackground());
      }

      this.setFont(table.getFont());
      if(hasFocus) {
         Border border = null;
         if(isSelected) {
            border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
         }

         if(border == null) {
            border = UIManager.getBorder("Table.focusCellHighlightBorder");
         }

         this.setBorder(border);
         if(!isSelected && table.isCellEditable(row, column)) {
            Color col = UIManager.getColor("Table.focusCellForeground");
            if(col != null) {
               super.setForeground(col);
            }

            col = UIManager.getColor("Table.focusCellBackground");
            if(col != null) {
               super.setBackground(col);
            }
         }
      } else {
         this.setBorder(getNoFocusBorder());
      }

      this.setValue(value);
      return this;
   }

   public boolean isOpaque() {
      Color back = this.getBackground();
      Container p = this.getParent();
      if(p != null) {
         p = p.getParent();
      }

      boolean colorMatch = back != null && p != null && back.equals(p.getBackground()) && p.isOpaque();
      return !colorMatch && super.isOpaque();
   }

   public void invalidate() {}

   public void validate() {}

   public void revalidate() {}

   public void repaint(long tm, int x, int y, int width, int height) {}

   public void repaint(Rectangle r) {}

   public void repaint() {}

   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
      if(propertyName == "text") {
         super.firePropertyChange(propertyName, oldValue, newValue);
      }

   }

   public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}

   protected void setValue(Object value) {
      this.setText(value == null?"":value.toString());
   }

}
