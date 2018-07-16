// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;

public class WorksheetCellRef extends CellRef {

   private String mWorksheet;


   public WorksheetCellRef(WorksheetCellRef other) {
      super(other);
      this.mWorksheet = other.mWorksheet;
   }

   public WorksheetCellRef(String worksheet, CellRef other) {
      super(other);
      this.mWorksheet = worksheet;
   }

   public WorksheetCellRef(String worksheet, int col, int row) {
      super(col, row);
      this.mWorksheet = worksheet;
   }

   public WorksheetCellRef(String worksheet, int column, boolean columnAbsolute, int row, boolean rowAbsolute) {
      super(column, columnAbsolute, row, rowAbsolute);
      this.mWorksheet = worksheet;
   }

   public String getWorksheet() {
      return this.mWorksheet;
   }

   public void setWorksheet(String worksheet) {
      this.mWorksheet = worksheet;
   }

   public Cell getCell(Worksheet worksheet, int currentRow, int currentColumn) {
      Worksheet refWorksheet = worksheet.getWorkbook().getWorksheet(this.mWorksheet);
      return refWorksheet == null?null:super.getCell(refWorksheet, currentRow, currentColumn);
   }

   public CellRef inverseReference(Worksheet worksheet, int row, int column) {
      WorksheetCellRef inverseRef = new WorksheetCellRef(worksheet.getName(), this);
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

   public Worksheet getWorksheet(Worksheet worksheet) {
      return worksheet.getWorkbook().getWorksheet(this.mWorksheet);
   }

   public boolean renameWorksheetReference(String oldName, String newName) {
      if(this.mWorksheet.equals(oldName)) {
         this.mWorksheet = newName;
         return true;
      } else {
         return false;
      }
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(obj instanceof WorksheetCellRef) {
         WorksheetCellRef other1 = (WorksheetCellRef)obj;
         return (this.mWorksheet == null && other1.mWorksheet == null || this.mWorksheet != null && other1.mWorksheet != null && this.mWorksheet.equals(other1.mWorksheet)) && this.isColumnAbsolute() == other1.isColumnAbsolute() && this.getColumn() == other1.getColumn() && this.isRowAbsolute() == other1.isRowAbsolute() && this.getRow() == other1.getRow();
      } else if(obj instanceof CellRef && this.mWorksheet == null) {
         CellRef other = (CellRef)obj;
         return this.isColumnAbsolute() == other.isColumnAbsolute() && this.getColumn() == other.getColumn() && this.isRowAbsolute() == other.isRowAbsolute() && this.getRow() == other.getRow();
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(" worksheet:").append(this.mWorksheet);
      sb.append(super.toString());
      return sb.toString();
   }

   public Object clone() throws CloneNotSupportedException {
      WorksheetCellRef copy = (WorksheetCellRef)super.clone();
      copy.mWorksheet = this.mWorksheet;
      return copy;
   }
}
