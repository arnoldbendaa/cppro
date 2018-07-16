// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.table;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.awt.table.CellSpanModel;
import com.cedar.cp.util.awt.table.MultiSpanCellTable$1;
import com.cedar.cp.util.awt.table.MultiSpanCellTableUI;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class MultiSpanCellTable extends JTable {

   protected static final int PREFERRED_MARGIN = 4;
   protected Map<Object, TableCellRenderer> mFixedRenderers = new HashMap();
   private static final int HEADER_MARGIN = 5;


   public MultiSpanCellTable(TableModel tbl) {
      super(tbl);
      this.setUI(new MultiSpanCellTableUI());
      this.getTableHeader().addMouseListener(new MultiSpanCellTable$1(this));
   }

   protected int getHitColumn(Point p, boolean insideMargin) {
      JTableHeader header = this.getTableHeader();
      int col = header.columnAtPoint(p);
      if(col != -1) {
         Point leftP = new Point(p.x - 5, p.y);
         int leftCol = header.columnAtPoint(leftP);
         if(insideMargin && leftCol != col) {
            return col;
         } else {
            Point rightP = new Point(p.x + 5, p.y);
            int rightCol = header.columnAtPoint(rightP);
            return insideMargin && rightCol != col?col:(insideMargin?-1:(col == leftCol && col == rightCol?col:-1));
         }
      } else {
         return -1;
      }
   }

   private RTree.Rect getCellRect(int row, int column) {
      TableModel tm = this.getModel();
      return tm instanceof CellSpanModel?((CellSpanModel)tm).getCellRect(row, column):null;
   }

   public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
      Rectangle sRect = super.getCellRect(row, column, includeSpacing);
      if(row >= 0 && column >= 0 && this.getRowCount() > row && this.getColumnCount() > column) {
         int[] n = new int[]{1, 1};
         RTree.Rect mergeRect = this.getCellRect(row, column);
         if(mergeRect != null) {
            n[0] = mergeRect.mEndRow - mergeRect.getStartRow() + 1;
            n[1] = mergeRect.mEndColumn - mergeRect.getStartColumn() + 1;
            row = mergeRect.mStartRow;
            column = mergeRect.mStartColumn;
         }

         int index = 0;
         Rectangle cellFrame = new Rectangle();
         int aCellHeight = this.rowHeight + this.rowMargin;
         cellFrame.y = row * aCellHeight;
         cellFrame.height = n[0] * aCellHeight;

         Enumeration enumeration;
         for(enumeration = this.getColumnModel().getColumns(); enumeration.hasMoreElements(); ++index) {
            TableColumn spacing = (TableColumn)enumeration.nextElement();
            cellFrame.width = spacing.getWidth();
            if(index == column) {
               break;
            }

            cellFrame.x += cellFrame.width;
         }

         for(int var13 = 0; var13 < n[1] - 1 && enumeration.hasMoreElements(); ++var13) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            cellFrame.width += aColumn.getWidth();
         }

         if(!includeSpacing) {
            Dimension var14 = this.getIntercellSpacing();
            cellFrame.setBounds(cellFrame.x + var14.width / 2, cellFrame.y + var14.height / 2, cellFrame.width - var14.width, cellFrame.height - var14.height);
         }

         return cellFrame;
      } else {
         return sRect;
      }
   }

   private int[] rowColumnAtPoint(Point point) {
      int[] retValue = new int[]{-1, -1};
      int row = point.y / (this.rowHeight + this.rowMargin);
      if(row >= 0 && this.getRowCount() > row) {
         int column = this.getColumnModel().getColumnIndexAtX(point.x);
         RTree.Rect mergeRect = this.getCellRect(row, column);
         if(mergeRect == null) {
            retValue[0] = row;
            retValue[1] = column;
         } else {
            retValue[0] = mergeRect.mStartRow;
            retValue[1] = mergeRect.mStartColumn;
         }

         return retValue;
      } else {
         return retValue;
      }
   }

   public int rowAtPoint(Point point) {
      return this.rowColumnAtPoint(point)[0];
   }

   public int columnAtPoint(Point point) {
      return this.rowColumnAtPoint(point)[1];
   }

   public void columnSelectionChanged(ListSelectionEvent e) {
      this.repaint();
   }

   public void valueChanged(ListSelectionEvent e) {
      int firstIndex = e.getFirstIndex();
      int lastIndex = e.getLastIndex();
      if(firstIndex == -1 && lastIndex == -1) {
         this.repaint();
      }

      Rectangle dirtyRegion = this.getCellRect(firstIndex, 0, false);
      int numCoumns = this.getColumnCount();
      int index = firstIndex;

      int i;
      for(i = 0; i < numCoumns; ++i) {
         dirtyRegion.add(this.getCellRect(index, i, false));
      }

      index = lastIndex;

      for(i = 0; i < numCoumns; ++i) {
         dirtyRegion.add(this.getCellRect(index, i, false));
      }

      this.repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
   }

   public void tableChanged(TableModelEvent e) {
      int modelColumn = e.getColumn();
      int start = e.getFirstRow();
      int end = e.getLastRow();
      if(e != null && e.getFirstRow() != -1 && e.getType() == 0 && start == end && modelColumn != -1) {
         int column = this.convertColumnIndexToView(modelColumn);
         Rectangle dirtyRegion = this.getCellRect(start, column, false);
         this.repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
      } else {
         super.tableChanged(e);
      }

   }
}
