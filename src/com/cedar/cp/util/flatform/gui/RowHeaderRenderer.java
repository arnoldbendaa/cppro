// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.JTableHeader;

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

   public RowHeaderRenderer(JTable table) {
      JTableHeader header = table.getTableHeader();
      this.setOpaque(true);
      this.setBorder(BorderFactory.createBevelBorder(0));
      this.setHorizontalAlignment(0);
      this.setForeground(header.getForeground());
      this.setBackground(header.getBackground());
      this.setFont(header.getFont());
   }

   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      this.setText(value == null?"":value.toString());
      return this;
   }
}
