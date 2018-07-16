// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.WorksheetCellRef;
import java.io.Serializable;

public class CellRef implements CellRangeRef, Serializable {

   private int mColumn;
   private boolean mColumnAbsolute;
   private int mRow;
   private boolean mRowAbsolute;


   public CellRef() {}

   public CellRef(CellRef cellRef) {
      this(cellRef.getColumn(), cellRef.isColumnAbsolute(), cellRef.getRow(), cellRef.isRowAbsolute());
   }

   public CellRef(int column, int row) {
      this.setColumn(column);
      this.mRow = row;
   }

   public CellRef(int column, boolean columnAbsolute, int row, boolean rowAbsolute) {
      this.setColumn(column);
      this.mColumnAbsolute = columnAbsolute;
      this.mRow = row;
      this.mRowAbsolute = rowAbsolute;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public void setColumn(int column) {
      this.mColumn = column;
   }

   public int getAbsoluteColumn(int columnOrigin) {
      return this.isColumnAbsolute()?this.getColumn():columnOrigin + this.getColumn();
   }

   public int getAbsoluteRow(int rowOrigin) {
      return this.isRowAbsolute()?this.getRow():rowOrigin + this.getRow();
   }

   public boolean isColumnAbsolute() {
      return this.mColumnAbsolute;
   }

   public void setColumnAbsolute(boolean columnAbsolute) {
      this.mColumnAbsolute = columnAbsolute;
   }

   public int getRow() {
      return this.mRow;
   }

   public void setRow(int row) {
      this.mRow = row;
   }

   public boolean isRowAbsolute() {
      return this.mRowAbsolute;
   }

   public void setRowAbsolute(boolean rowAbsolute) {
      this.mRowAbsolute = rowAbsolute;
   }

   public void getSheetText(StringBuilder sb, Worksheet owningWorksheet, int originRow, int originColumn) {
      if(this.isColumnAbsolute()) {
         sb.append('$');
      }

      sb.append(this.getWorksheet(owningWorksheet).getColumnName(this.getAbsoluteColumn(originColumn)));
      if(this.isRowAbsolute()) {
         sb.append('$');
      }

      sb.append(this.getAbsoluteRow(originRow) + 1);
   }

   public String getRowColumnText() {
      StringBuilder sb = new StringBuilder();
      sb.append("r");
      if(this.isRowAbsolute()) {
         sb.append(this.mRow);
      } else {
         sb.append('[').append(this.mRow).append(']');
      }

      sb.append("c");
      if(this.isColumnAbsolute()) {
         sb.append(this.mColumn);
      } else {
         sb.append('[').append(this.mColumn).append(']');
      }

      return sb.toString();
   }

   public String toString() {
      return this.getRowColumnText();
   }

   public CellRef inverseReference(Worksheet worksheet, int row, int column) {
      CellRef inverseRef = new CellRef(this);
      if(!this.isColumnAbsolute()) {
         inverseRef.setColumn(this.getColumn() * -1);
      } else {
         inverseRef.setColumn(column);
      }

      if(!this.isRowAbsolute()) {
         inverseRef.setRow(this.getRow() * -1);
      } else {
         inverseRef.setRow(row);
      }

      return inverseRef;
   }

   public CellRef getStartRef() {
      return this;
   }

   public CellRef getEndRef() {
      return this;
   }

   public boolean isSingleCell() {
      return true;
   }

   public int getAbsoluteStartRow(int rowOrigin) {
      return this.getAbsoluteRow(rowOrigin);
   }

   public int getAbsoluteEndRow(int rowOrigin) {
      return this.getAbsoluteRow(rowOrigin);
   }

   public int getAbsoluteStartColumn(int columnOrigin) {
      return this.getAbsoluteColumn(columnOrigin);
   }

   public int getAbsoluteEndColumn(int columnOrigin) {
      return this.getAbsoluteColumn(columnOrigin);
   }

   public Cell getCell(Worksheet worksheet, int currentRow, int currentColumn) {
      return (Cell)worksheet.get(this.getAbsoluteRow(currentRow), this.getAbsoluteColumn(currentColumn));
   }

   public Worksheet getWorksheet(Worksheet worksheet) {
      return worksheet;
   }

   public boolean renameWorksheetReference(String oldName, String newName) {
      return false;
   }

   public boolean insertColumn(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      if(this.getWorksheet(owningWorksheet) == targetWorksheet) {
         if(!this.isColumnAbsolute() && owningWorksheet == targetWorksheet) {
            if(originColumn >= columnIndex) {
               originColumn -= columnCount;
            }

            if(originColumn < columnIndex && this.getAbsoluteColumn(originColumn) >= columnIndex) {
               this.mColumn += columnCount;
               return true;
            }

            if(originColumn >= columnIndex && this.getAbsoluteColumn(originColumn) < columnIndex) {
               this.mColumn -= columnCount;
               return true;
            }
         } else if(this.getAbsoluteColumn(originColumn) >= columnIndex) {
            this.mColumn += columnCount;
            return true;
         }
      } else if(owningWorksheet == targetWorksheet && !this.isColumnAbsolute()) {
         if(originColumn >= columnIndex) {
            originColumn -= columnCount;
         }

         if(originColumn >= columnIndex) {
            this.mColumn -= columnCount;
            return true;
         }
      }

      return false;
   }

   public boolean isInsertColumnRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      if(this.getWorksheet(owningWorksheet) != targetWorksheet) {
         if(owningWorksheet == targetWorksheet && !this.isColumnAbsolute()) {
            if(originColumn >= columnIndex) {
               originColumn -= columnCount;
            }

            return originColumn >= columnIndex;
         } else {
            return false;
         }
      } else if(owningWorksheet == targetWorksheet) {
         if(originColumn >= columnIndex) {
            originColumn -= columnCount;
         }

         return this.isColumnAbsolute() && this.getAbsoluteColumn(originColumn) >= columnIndex || !this.isColumnAbsolute() && originColumn < columnIndex && this.getAbsoluteColumn(originColumn) >= columnIndex || originColumn >= columnIndex && this.getAbsoluteColumn(originColumn) < columnIndex;
      } else {
         return this.getAbsoluteColumn(originColumn) >= columnIndex;
      }
   }

   public boolean removeColumn(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      if(this.getWorksheet(owningWorksheet) == targetWorksheet) {
         if(!this.isColumnAbsolute() && owningWorksheet == targetWorksheet) {
            if(originColumn >= columnIndex) {
               originColumn += columnCount;
            }

            if(originColumn < columnIndex && this.getAbsoluteColumn(originColumn) >= columnIndex) {
               this.mColumn -= columnCount;
               return true;
            }

            if(originColumn >= columnIndex && this.getAbsoluteColumn(originColumn) < columnIndex) {
               this.mColumn += columnCount;
               return true;
            }
         } else if(this.getAbsoluteColumn(originColumn) >= columnIndex) {
            this.mColumn -= columnCount;
            return true;
         }
      } else if(owningWorksheet == targetWorksheet && !this.isColumnAbsolute()) {
         if(originColumn >= columnIndex) {
            originColumn += columnCount;
         }

         if(originColumn >= columnIndex) {
            this.mColumn += columnCount;
            return true;
         }
      }

      return false;
   }

   public boolean isRemoveColumnRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      if(this.getWorksheet(owningWorksheet) != targetWorksheet) {
         if(owningWorksheet == targetWorksheet && !this.isColumnAbsolute()) {
            if(originColumn >= columnIndex) {
               originColumn += columnCount;
            }

            return originColumn >= columnIndex;
         } else {
            return false;
         }
      } else if(owningWorksheet == targetWorksheet) {
         if(originColumn >= columnIndex) {
            originColumn += columnCount;
         }

         return this.isColumnAbsolute() && this.getAbsoluteColumn(originColumn) >= columnIndex || !this.isColumnAbsolute() && originColumn < columnIndex && this.getAbsoluteColumn(originColumn) >= columnIndex || originColumn >= columnIndex && this.getAbsoluteColumn(originColumn) < columnIndex;
      } else {
         return this.getAbsoluteColumn(originColumn) >= columnIndex;
      }
   }

   public boolean insertRow(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      if(this.getWorksheet(owningWorksheet) == targetWorksheet) {
         if(!this.isColumnAbsolute() && owningWorksheet == targetWorksheet) {
            if(originRow >= rowIndex) {
               originRow -= rowCount;
            }

            if(originRow < rowIndex && this.getAbsoluteRow(originRow) >= rowIndex) {
               this.mRow += rowCount;
               return true;
            }

            if(originRow >= rowIndex && this.getAbsoluteRow(originRow) < rowIndex) {
               this.mRow -= rowCount;
               return true;
            }
         } else if(this.getAbsoluteRow(originRow) >= rowIndex) {
            this.mRow += rowCount;
            return true;
         }
      } else if(owningWorksheet == targetWorksheet && !this.isRowAbsolute()) {
         if(originRow >= rowIndex) {
            originRow -= rowCount;
         }

         if(originRow >= rowIndex) {
            this.mRow -= rowCount;
            return true;
         }
      }

      return false;
   }

   public boolean isInsertRowRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      if(this.getWorksheet(owningWorksheet) != targetWorksheet) {
         if(owningWorksheet == targetWorksheet && !this.isRowAbsolute()) {
            if(originRow >= rowIndex) {
               originRow -= rowCount;
            }

            return originRow >= rowIndex;
         } else {
            return false;
         }
      } else if(owningWorksheet == targetWorksheet) {
         if(originRow >= rowIndex) {
            originRow -= rowCount;
         }

         return this.isRowAbsolute() && this.getAbsoluteRow(originRow) >= rowIndex || !this.isRowAbsolute() && originRow < rowIndex && this.getAbsoluteRow(originRow) >= rowIndex || originRow >= rowIndex && this.getAbsoluteRow(originRow) < rowIndex;
      } else {
         return this.getAbsoluteRow(originRow) >= rowIndex;
      }
   }

   public boolean removeRow(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      if(this.getWorksheet(owningWorksheet) == targetWorksheet) {
         if(!this.isRowAbsolute() && owningWorksheet == targetWorksheet) {
            if(originRow >= rowIndex) {
               originRow += rowCount;
            }

            if(originRow < rowIndex && this.getAbsoluteRow(originRow) >= rowIndex) {
               this.mRow -= rowCount;
               return true;
            }

            if(originRow >= rowIndex && this.getAbsoluteRow(originRow) < rowIndex) {
               this.mRow += rowCount;
               return true;
            }
         } else if(this.getAbsoluteRow(originRow) >= rowIndex) {
            this.mRow -= rowCount;
            return true;
         }
      } else if(owningWorksheet == targetWorksheet && !this.isRowAbsolute()) {
         if(originRow >= rowIndex) {
            originRow += rowCount;
         }

         if(originRow >= rowIndex) {
            this.mRow += rowCount;
            return true;
         }
      }

      return false;
   }

   public boolean isRemoveRowRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      if(this.getWorksheet(owningWorksheet) != targetWorksheet) {
         if(owningWorksheet == targetWorksheet && !this.isRowAbsolute()) {
            if(originRow >= rowIndex) {
               originRow += rowCount;
            }

            return originRow >= rowIndex;
         } else {
            return false;
         }
      } else if(owningWorksheet == targetWorksheet) {
         if(originRow >= rowIndex) {
            originRow += rowCount;
         }

         return this.isRowAbsolute() && this.getAbsoluteRow(originRow) >= rowIndex || !this.isRowAbsolute() && originRow < rowIndex && this.getAbsoluteRow(originRow) >= rowIndex || originRow >= rowIndex && this.getAbsoluteRow(originRow) < rowIndex;
      } else {
         return this.getAbsoluteRow(originRow) >= rowIndex;
      }
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(obj instanceof WorksheetCellRef) {
         return obj.equals(this);
      } else if(!(obj instanceof CellRef)) {
         return super.equals(obj);
      } else {
         CellRef other = (CellRef)obj;
         return this.mColumnAbsolute == other.mColumnAbsolute && this.mColumn == other.mColumn && this.mRowAbsolute == other.mRowAbsolute && this.mRow == other.mRow;
      }
   }

   public int hashCode() {
      return this.mColumn + this.mRow;
   }

   public Object clone() throws CloneNotSupportedException {
      CellRef copy = (CellRef)super.clone();
      copy.mColumn = this.mColumn;
      copy.mColumnAbsolute = this.mColumnAbsolute;
      copy.mRow = this.mRow;
      copy.mRowAbsolute = this.mRowAbsolute;
      return copy;
   }

   public boolean isVector() {
      return true;
   }

   public int getWidth() {
      return 1;
   }

   public int getDepth() {
      return 1;
   }
}
