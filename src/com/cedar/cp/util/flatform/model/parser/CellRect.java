// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;

public class CellRect implements Comparable<CellRect> {

   private Cell mCell;
   private Worksheet mWorksheet;
   private int mStartRow;
   private int mStartColumn;
   private int mEndColumn;
   private int mEndRow;


   public CellRect(Cell cell, Worksheet worksheet, int startRow, int startColumn) {
      this.mCell = cell;
      this.mWorksheet = worksheet;
      this.mStartRow = startRow;
      this.mStartColumn = startColumn;
      this.mEndColumn = startColumn;
      this.mEndRow = startRow;
   }

   public CellRect(Cell cell, Worksheet worksheet, int startRow, int startColumn, int endRow, int endColumn) {
      this.mCell = cell;
      this.mWorksheet = worksheet;
      this.mStartRow = startRow;
      this.mStartColumn = startColumn;
      this.mEndColumn = endColumn;
      this.mEndRow = endRow;
   }

   public int getStartRow() {
      return this.mStartRow;
   }

   public boolean intersects(CellRect other) {
      return other.mStartColumn <= this.mEndColumn && other.mEndColumn >= this.mStartColumn && other.mStartRow <= this.mEndRow && other.mEndRow >= this.mStartRow;
   }

   public Cell getCell() {
      return this.mCell;
   }

   public void setStartRow(int startRow) {
      this.mStartRow = startRow;
   }

   public int getStartColumn() {
      return this.mStartColumn;
   }

   public void setStartColumn(int startColumn) {
      this.mStartColumn = startColumn;
   }

   public int getEndColumn() {
      return this.mEndColumn;
   }

   public void setEndColumn(int endColumn) {
      this.mEndColumn = endColumn;
   }

   public int getEndRow() {
      return this.mEndRow;
   }

   public void setEndRow(int endRow) {
      this.mEndRow = endRow;
   }

   public Worksheet getWorksheet() {
      return this.mWorksheet;
   }

   public int compareTo(CellRect o) {
      int diff = this.mStartRow - o.mStartRow;
      if(diff != 0) {
         return diff;
      } else {
         diff = this.mStartColumn - o.mStartColumn;
         if(diff != 0) {
            return diff;
         } else {
            diff = this.mEndRow - o.mEndRow;
            if(diff != 0) {
               return diff;
            } else {
               diff = this.mEndColumn - o.mEndColumn;
               if(diff != 0) {
                  return diff;
               } else {
                  diff = this.mCell.getRow() - o.mCell.getRow();
                  if(diff != 0) {
                     return diff;
                  } else {
                     diff = this.mCell.getColumn() - o.mCell.getColumn();
                     return diff != 0?diff:this.mCell.getWorksheet().getName().compareTo(o.mCell.getWorksheet().getName());
                  }
               }
            }
         }
      }
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(!(obj instanceof CellRect)) {
         return false;
      } else {
         CellRect other = (CellRect)obj;
         return this.mStartRow == other.mStartRow && this.mStartColumn == other.mStartColumn && this.mEndColumn == other.mEndColumn && this.mEndRow == other.mEndRow && this.mWorksheet.getName() == other.mWorksheet.getName()?this.mCell.getWorksheet().getName().equals(other.mCell.getWorksheet().getName()):false;
      }
   }

   public int hashCode() {
      return this.mStartRow + this.mStartColumn;
   }

   public String toString() {
      StringBuilder sb = (new StringBuilder()).append(" worksheet:").append(this.mWorksheet != null?this.mWorksheet.getName():"no worksheet").append(" startRow:").append(this.mStartRow).append(" startCol:").append(this.mStartColumn).append(" endRow:").append(this.mEndRow).append(" endColumn:").append(this.mEndColumn);
      return sb.toString();
   }
}
