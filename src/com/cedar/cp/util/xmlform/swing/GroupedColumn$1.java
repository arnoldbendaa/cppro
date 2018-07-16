// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

final class GroupedColumn$1 extends DefaultTableCellRenderer {

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      JTableHeader header = table.getTableHeader();
      if(header != null) {
         this.setForeground(header.getForeground());
         this.setBackground(header.getBackground());
         this.setFont(header.getFont());
      }

      this.setHorizontalAlignment(0);
      this.setText(value == null?"":value.toString());
      this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      return this;
   }
}
