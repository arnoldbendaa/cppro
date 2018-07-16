// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.swing.FormTableModel;
import com.cedar.cp.util.xmlform.swing.ValidationMessageStore;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class FormCellRenderer extends DefaultTableCellRenderer {

   private Color mProtectedColor = new Color(228, 244, 254);
   private DecimalFormat mFormatter = new DecimalFormat();


   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      this.setToolTipText((String)null);
      Color bgColor = this.mProtectedColor;
      this.setHorizontalAlignment(4);
      TableModel model = table.getModel();
      String columnId = null;
      FormTableModel ftModel;
      if(model instanceof FormTableModel) {
         ftModel = (FormTableModel)model;
         columnId = ftModel.getColumnId(column);
         int format = ftModel.getColumnAlignment(column);
         if(format == 0) {
            this.setHorizontalAlignment(2);
         } else if(format == 1) {
            this.setHorizontalAlignment(0);
         }

         Color color = ftModel.getColumnBackgroundColor(column);
         if(color != null) {
            bgColor = color;
         }
      }

      String format1;
      if(table.getModel().isCellEditable(row, column)) {
         if(table.getModel() instanceof ValidationMessageStore && columnId != null) {
            ValidationMessageStore ftModel1 = (ValidationMessageStore)table.getModel();
            format1 = ftModel1.getValidationMessage(Integer.valueOf(row), columnId);
            if(format1 != null) {
               this.setBackground(Color.RED);
               this.setToolTipText(format1);
            } else {
               this.setBackground(table.getBackground());
            }
         } else {
            this.setBackground(table.getBackground());
         }
      } else {
         this.setBackground(bgColor);
      }

      if(value instanceof BigDecimal && model instanceof FormTableModel) {
         ftModel = (FormTableModel)model;
         if(ftModel.isColumnBlankWhenZero(column) && ((BigDecimal)value).doubleValue() == 0.0D) {
            value = "";
         } else {
            format1 = ftModel.getColumnFormat(column);
            this.mFormatter.applyPattern(format1);
            value = this.mFormatter.format(((BigDecimal)value).doubleValue());
         }
      }

      return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
   }
}
