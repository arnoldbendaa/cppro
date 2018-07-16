// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;

public class TableRowResizer extends MouseInputAdapter {

   public static Cursor resizeCursor = Cursor.getPredefinedCursor(8);
   private int mouseYOffset;
   private int resizingRow;
   private Cursor otherCursor;
   private JTable table;


   public TableRowResizer(JTable table) {
      this.otherCursor = resizeCursor;
      this.table = table;
      table.addMouseListener(this);
      table.addMouseMotionListener(this);
   }

   private int getResizingRow(Point p) {
      return this.getResizingRow(p, this.table.rowAtPoint(p));
   }

   private int getResizingRow(Point p, int row) {
      if(row == -1) {
         return -1;
      } else {
         int col = this.table.columnAtPoint(p);
         if(col == -1) {
            return -1;
         } else {
            Rectangle r = this.table.getCellRect(row, col, true);
            r.grow(0, -3);
            if(r.contains(p)) {
               return -1;
            } else {
               int midPoint = r.y + r.height / 2;
               int rowIndex = p.y < midPoint?row - 1:row;
               return rowIndex;
            }
         }
      }
   }

   public void mousePressed(MouseEvent e) {
      Point p = e.getPoint();
      this.resizingRow = this.getResizingRow(p);
      this.mouseYOffset = p.y - this.table.getRowHeight(this.resizingRow);
   }

   private void swapCursor() {
      Cursor tmp = this.table.getCursor();
      this.table.setCursor(this.otherCursor);
      this.otherCursor = tmp;
   }

   public void mouseMoved(MouseEvent e) {
      if(this.getResizingRow(e.getPoint()) >= 0 != (this.table.getCursor() == resizeCursor)) {
         this.swapCursor();
      }

   }

   public void mouseDragged(MouseEvent e) {
      int mouseY = e.getY();
      if(this.resizingRow >= 0) {
         int newHeight = mouseY - this.mouseYOffset;
         if(newHeight > 0) {
            this.table.setRowHeight(this.resizingRow, newHeight);
         }
      }

   }

}
