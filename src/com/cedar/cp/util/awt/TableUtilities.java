// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;

public class TableUtilities {

   protected static final int PREFERRED_MARGIN = 4;
   private static final Logger mLog = Logger.getLogger(TableUtilities.class);


   public static void calculatePreferredColumnSizes(JTable table) {
      for(int i = 0; i < table.getColumnCount(); ++i) {
         initTableColumnSize(table, i);
      }

   }

   public static void initTableColumnSizes(JTable table) {
      long s = System.currentTimeMillis();

      for(int e = 0; e < table.getColumnCount(); ++e) {
         initTableColumnSize(table, e);
      }

      long var5 = System.currentTimeMillis();
      mLog.debug("TableUtilities.initTableColumnSizes " + (var5 - s));
   }

   public static void initTableColumnSize(JTable table, int col) {
      TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
      TableColumn column = table.getColumnModel().getColumn(col);
      TableCellRenderer renderer = column.getCellRenderer();
      if(renderer == null) {
         renderer = headerRenderer;
      }

      int prefWidth = 2 + getColumnPreferredWidth(table, renderer, col);
      column.setPreferredWidth(prefWidth);
      column.sizeWidthToFit();
   }

   public static int getColumnPreferredWidth(JTable jTable, TableCellRenderer headerRenderer, int colIndex) {
      TableColumn column = jTable.getColumnModel().getColumn(colIndex);
      Component comp = headerRenderer.getTableCellRendererComponent((JTable)null, column.getHeaderValue(), false, false, 0, colIndex);
      int minWidth = 4 + comp.getPreferredSize().width + 4;

      for(int r = 0; r < jTable.getRowCount(); ++r) {
         int cellWidth = getCellPreferredWidth(jTable, r, colIndex);
         if(cellWidth > minWidth) {
            minWidth = cellWidth;
         }
      }

      return minWidth;
   }

   public static int getCellPreferredWidth(JTable table, int row, int col) {
      int cellWidth = 0;
      Object cellValue = table.getValueAt(row, col);
      if(cellValue != null) {
         TableCellRenderer defaultRenderer = table.getDefaultRenderer(table.getModel().getColumnClass(col));
         if(defaultRenderer != null) {
            Component comp = defaultRenderer.getTableCellRendererComponent(table, cellValue, false, false, row, col);
            cellWidth = 4 + comp.getPreferredSize().width;
         }
      }

      return cellWidth;
   }

}
