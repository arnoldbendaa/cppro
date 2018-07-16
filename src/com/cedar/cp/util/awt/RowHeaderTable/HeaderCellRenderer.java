// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

class HeaderCellRenderer extends DefaultTableCellRenderer {

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      TableModel model = table.getModel();
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
