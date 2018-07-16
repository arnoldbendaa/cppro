// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.table;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.awt.table.CellSpanModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class MultiSpanCellTableUI extends BasicTableUI {

   private Set<RTree.Rect> mDrawnRectangles = new HashSet();


   public void paint(Graphics g, JComponent c) {
      Rectangle oldClipBounds = g.getClipBounds();
      Rectangle clipBounds = new Rectangle(oldClipBounds);
      int tableWidth = this.table.getColumnModel().getTotalColumnWidth();
      clipBounds.width = Math.min(clipBounds.width, tableWidth);
      g.setClip(clipBounds);
      int firstIndex = this.table.rowAtPoint(new Point(0, clipBounds.y));
      int lastIndex = this.table.getRowCount() - 1;
      Rectangle rowRect = new Rectangle(0, 0, tableWidth, this.table.getRowHeight() + this.table.getRowMargin());
      rowRect.y = firstIndex * rowRect.height;
      this.mDrawnRectangles.clear();

      for(int index = firstIndex; index <= lastIndex; ++index) {
         if(rowRect.intersects(clipBounds)) {
            this.paintRow(g, index);
         }

         rowRect.y += rowRect.height;
      }

      g.setClip(oldClipBounds);
   }

   private void paintRow(Graphics g, int row) {
      Rectangle rect = g.getClipBounds();
      boolean drawn = false;
      int numColumns = this.table.getColumnCount();

      for(int column = 0; column < numColumns; ++column) {
         Rectangle cellRect = this.table.getCellRect(row, column, true);
         if(cellRect.intersects(rect)) {
            drawn = true;
            RTree.Rect mergeRect = this.getCellRect(row, column);
            if(mergeRect != null) {
               if(!this.mDrawnRectangles.contains(mergeRect)) {
                  this.mDrawnRectangles.add(mergeRect);
                  int cellRow = mergeRect.getStartRow();
                  int cellColumn = mergeRect.getStartColumn();
                  this.paintCell(g, cellRect, cellRow, cellColumn);
               }
            } else {
               this.paintCell(g, cellRect, row, column);
            }
         } else if(drawn) {
            break;
         }
      }

   }

   private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
      int spacingHeight = this.table.getRowMargin();
      int spacingWidth = this.table.getColumnModel().getColumnMargin();
      Color c = g.getColor();
      if(this.table.getShowHorizontalLines()) {
         g.setColor(this.table.getGridColor());
         g.drawLine(cellRect.x, cellRect.y + cellRect.height - 1, cellRect.x + cellRect.width - 1, cellRect.y + cellRect.height - 1);
      }

      if(this.table.getShowVerticalLines()) {
         g.setColor(this.table.getGridColor());
         g.drawLine(cellRect.x + cellRect.width - 1, cellRect.y, cellRect.x + cellRect.width - 1, cellRect.y + cellRect.height - 1);
      }

      g.setColor(c);
      cellRect.setBounds(cellRect.x + spacingWidth / 2, cellRect.y + spacingHeight / 2, cellRect.width - spacingWidth, cellRect.height - spacingHeight);
      if(this.table.isEditing() && this.table.getEditingRow() == row && this.table.getEditingColumn() == column) {
         Component renderer1 = this.table.getEditorComponent();
         renderer1.setBounds(cellRect);
         renderer1.validate();
      } else {
         TableCellRenderer renderer = this.table.getCellRenderer(row, column);
         Component component = this.table.prepareRenderer(renderer, row, column);
         if(component.getParent() == null) {
            this.rendererPane.add(component);
         }

         this.rendererPane.paintComponent(g, component, this.table, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
      }

   }

   private RTree.Rect getCellRect(int row, int column) {
      TableModel tm = this.table.getModel();
      return tm instanceof CellSpanModel?((CellSpanModel)tm).getCellRect(row, column):null;
   }
}
