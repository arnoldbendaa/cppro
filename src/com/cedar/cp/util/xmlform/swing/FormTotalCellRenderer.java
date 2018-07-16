// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.ColumnFormat;
import com.cedar.cp.util.xmlform.ColumnTotal;
import com.cedar.cp.util.xmlform.swing.NonTableFormTableModel;
import com.cedar.cp.util.xmlform.swing.TotalBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class FormTotalCellRenderer extends DefaultTableCellRenderer {

   private Border mTotalBorder;
   private DecimalFormat mFormatter = new DecimalFormat();
   private Font mFont;


   public FormTotalCellRenderer() {
      this.mTotalBorder = new TotalBorder(Color.black);
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Color bgColor = table.getTableHeader().getBackground();
      this.setHorizontalAlignment(4);
      TableModel model = table.getModel();
      NonTableFormTableModel c;
      ColumnTotal total;
      if(model instanceof NonTableFormTableModel) {
         c = (NonTableFormTableModel)model;
         total = c.getTotalColumn(column);
         if(total != null) {
            int format = total.getIntAlignment();
            if(format == 0) {
               this.setHorizontalAlignment(2);
            } else if(format == 1) {
               this.setHorizontalAlignment(0);
            }

            ColumnFormat colFormat = total.getColumnFormat();
            Color color = colFormat.getBackgroundColor();
            if(color != null) {
               bgColor = color;
            }
         }
      }

      this.setBackground(bgColor);
      if(value instanceof BigDecimal && model instanceof NonTableFormTableModel) {
         c = (NonTableFormTableModel)model;
         total = c.getTotalColumn(column);
         if(total != null) {
            if(total.isBlankWhenZero() && ((BigDecimal)value).doubleValue() == 0.0D) {
               value = "";
            } else {
               String format1 = total.getFormat();
               this.mFormatter.applyPattern(format1);
               value = this.mFormatter.format(((BigDecimal)value).doubleValue());
            }
         }
      }

      Component c1 = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if(this.mFont == null) {
         this.mFont = table.getFont().deriveFont(1);
      }

      this.setFont(this.mFont);
      if(!hasFocus) {
         this.setBorder(this.mTotalBorder);
      }

      return c1;
   }
}
