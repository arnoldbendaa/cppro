// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class FitTableColumnsAction extends AbstractAction {

   public FitTableColumnsAction() {
      super("Fit Table Columns");
   }

   public void actionPerformed(ActionEvent ae) {
      JTable table = (JTable)ae.getSource();
      JTableHeader header = table.getTableHeader();
      int rowCount = table.getRowCount();
      Enumeration columns = table.getColumnModel().getColumns();

      while(columns.hasMoreElements()) {
         TableColumn column = (TableColumn)columns.nextElement();
         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
         int width = (int)table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();

         for(int row = 0; row < rowCount; ++row) {
            int preferedWidth = (int)table.getCellRenderer(row, col).getTableCellRendererComponent(table, table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
            width = Math.max(width, preferedWidth);
         }

         header.setResizingColumn(column);
         column.setWidth(width + table.getIntercellSpacing().width);
      }

   }
}
