// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetTable;
import com.cedar.cp.util.flatform.model.Cell;
import java.util.EventObject;

public class CellSelectionEvent extends EventObject {

   protected int mFirstRow;
   protected int mFirstColumn;
   protected int mRowCount;
   protected int mColumnCount;
   protected Cell mCell;


   public CellSelectionEvent(WorksheetTable source, int row, int column, int rowCount, int columnCount) {
      super(source);
      this.mFirstRow = row;
      this.mFirstColumn = column;
      this.mRowCount = rowCount;
      this.mColumnCount = columnCount;
   }

   public int getFirstRow() {
      return this.mFirstRow;
   }

   public void setFirstRow(int firstRow) {
      this.mFirstRow = firstRow;
   }

   public int getFirstColumn() {
      return this.mFirstColumn;
   }

   public void setFirstColumn(int firstColumn) {
      this.mFirstColumn = firstColumn;
   }

   public int getRowCount() {
      return this.mRowCount;
   }

   public int getColumnCount() {
      return this.mColumnCount;
   }

   public Cell getCell() {
      return this.mCell;
   }

   public void setCell(Cell cell) {
      this.mCell = cell;
   }
}
