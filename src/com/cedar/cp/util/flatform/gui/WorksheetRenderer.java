// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.SparseTableModel;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class WorksheetRenderer extends DefaultTableCellRenderer {

   private Border mNoFocusBorder = new EmptyBorder(1, 2, 1, 2);
   private int mViewingLayer = 0;
   private boolean mUnderlineText;
   private boolean mHasInputLayer;
   private boolean mHasOutputLayer;
   private boolean mDesignMode;
   private StringBuilder mToolTipText = new StringBuilder();


   public WorksheetRenderer(boolean designMode) {
      this.mDesignMode = designMode;
      this.setOpaque(true);
      this.setBorder(this.mNoFocusBorder);
      this.setBackground(Color.white);
      this.setHorizontalAlignment(4);
   }

   public void setViewLayer(int layer) {
      this.mViewingLayer = layer;
   }

   public void setValue(Object value) {
      this.mHasInputLayer = false;
      this.mHasOutputLayer = false;
      if(value instanceof Cell) {
         Cell cell = (Cell)value;
         switch(this.mViewingLayer) {
         case 0:
            this.setText(cell.getCellText());
            break;
         case 1:
            this.setText(cell.getFormulaText());
            break;
         case 2:
            String vi = cell.getInputMapping();
            this.setText(vi != null?vi:cell.getText());
            break;
         case 3:
            String vo = cell.getOutputMapping();
            this.setText(vo != null?vo:cell.getText());
         }

         this.mHasInputLayer = cell.getInputMapping() != null;
         this.mHasOutputLayer = cell.getOutputMapping() != null;
         this.mToolTipText.setLength(0);
         if(this.mDesignMode) {
            if(this.mHasInputLayer) {
               this.mToolTipText.append(cell.getInputMapping());
            }

            if(this.mHasInputLayer && this.mHasOutputLayer) {
               this.mToolTipText.append("\n");
            }

            if(this.mHasOutputLayer) {
               this.mToolTipText.append(cell.getOutputMapping());
            }
         }

         this.setToolTipText(this.mToolTipText.length() > 0?this.mToolTipText.toString():null);
      } else {
         super.setValue(value);
         this.setToolTipText((String)null);
      }

   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Cell cell = (Cell)value;
      CellFormat format = null;
      this.mUnderlineText = false;
      if(cell != null) {
         format = cell.getFormat();
      } else {
         format = ((SparseTableModel)table.getModel()).getWorksheet().getFormat(row, column, row, column);
      }

      if(isSelected) {
         super.setForeground(table.getSelectionForeground());
         super.setBackground(table.getSelectionBackground());
      } else {
         Color f = null;
         Color border = null;
         if(cell != null) {
            if((this.mViewingLayer != 1 || cell.getFormula() == null) && (this.mViewingLayer != 2 || cell.getInputMapping() == null) && (this.mViewingLayer != 3 || cell.getOutputMapping() == null)) {
               f = format.getTextColor();
               border = format.getBackgroundColor();
               if(cell.isNegative()) {
                  f = format.getNegativeColor();
               }
            } else {
               f = Color.blue;
               border = Color.cyan;
            }
         } else if(format != null) {
            f = format.getTextColor();
            border = format.getBackgroundColor();
         }

         super.setForeground(f != null?f:table.getForeground());
         super.setBackground(border != null?border:table.getBackground());
      }

      Font f1 = null;
      if(format != null) {
         f1 = format.getFont();
         this.mUnderlineText = format.isUnderlineFont();
      }

      if(f1 == null && format != null) {
         f1 = table.getFont();
         if(format.isBoldFont() != f1.isBold() || format.isItalicFont() != f1.isItalic() || format.getFontSize() != 0 && format.getFontSize() != f1.getSize()) {
            f1 = f1.deriveFont((format.isBoldFont()?1:0) | (format.isItalicFont()?2:0), format.getFontSize() != 0?(float)format.getFontSize():(float)f1.getSize());
         }
      }

      this.setFont(f1);
      Border border1;
      if(hasFocus) {
         border1 = null;
         if(isSelected) {
            border1 = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
         }

         if(border1 == null) {
            border1 = UIManager.getBorder("Table.focusCellHighlightBorder");
         }

         this.setBorder(border1);
         if(!isSelected && table.isCellEditable(row, column)) {
            Color cellBorder = UIManager.getColor("Table.focusCellForeground");
            if(cellBorder != null) {
               super.setForeground(cellBorder);
            }

            cellBorder = UIManager.getColor("Table.focusCellBackground");
            if(cellBorder != null) {
               super.setBackground(cellBorder);
            }
         }
      } else {
         border1 = this.mNoFocusBorder;
         if(format != null) {
            Border cellBorder1 = format.getBorder();
            if(cellBorder1 != null) {
               border1 = cellBorder1;
            }
         }

         this.setBorder(border1);
      }

      if(format != null) {
         this.setVerticalAlignment(format.getVerticalAlignment());
         this.setHorizontalAlignment(format.getHorizontalAlignment());
      } else {
         this.setVerticalAlignment(0);
         this.setHorizontalAlignment(4);
      }

      this.setValue(cell);
      return this;
   }

   public void paint(Graphics g) {
      super.paint(g);
      if(this.mDesignMode) {
         Insets underline;
         if(this.mHasInputLayer) {
            underline = this.getInsets();
            g.drawLine(underline.left, underline.top + 3, underline.left + 5, underline.top + 3);
            g.drawLine(underline.left + 4, underline.top + 2, underline.left + 4, underline.top + 4);
         }

         if(this.mHasOutputLayer) {
            underline = this.getInsets();
            g.drawLine(underline.left, underline.top + 3, underline.left + 5, underline.top + 3);
            g.drawLine(underline.left + 1, underline.top + 2, underline.left + 1, underline.top + 4);
         }
      }

      if(this.mUnderlineText) {
         Color underline1 = this.getForeground();
         String text = this.getText();
         FontMetrics fm = g.getFontMetrics();
         int w = fm.stringWidth(text);
         int h = fm.getHeight();
         Insets insets = this.getInsets();
         int y;
         if(this.getVerticalAlignment() == 1) {
            y = insets.top + h;
         } else if(this.getVerticalAlignment() == 0) {
            y = this.getHeight() / 2 + h / 2;
         } else {
            y = this.getHeight() - 1 - insets.bottom;
         }

         int x;
         if(this.getHorizontalAlignment() == 2) {
            x = insets.left;
         } else if(this.getHorizontalAlignment() == 0) {
            x = this.getWidth() / 2 - w / 2;
         } else {
            x = this.getWidth() - w - insets.right;
         }

         g.setColor(underline1);
         g.drawLine(x, y, x + w, y);
      }

   }
}
