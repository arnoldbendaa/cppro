// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class TableHeaderRenderer extends DefaultTableCellRenderer {

   public TableHeaderRenderer() {
      this.setHorizontalAlignment(0);
      this.setBorder(BorderFactory.createBevelBorder(0));
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(table != null) {
         JTableHeader header = table.getTableHeader();
         if(header != null) {
            this.setForeground(header.getForeground());
            this.setBackground(header.getBackground());
            this.setFont(header.getFont());
         }
      }

      this.setText(value == null?"":value.toString());
      return this;
   }
}
