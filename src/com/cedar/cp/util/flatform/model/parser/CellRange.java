// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import java.io.Serializable;

public class CellRange implements CellRangeRef, Serializable {

   private CellRef mStartRef;
   private CellRef mEndRef;


   public CellRange() {}

   public CellRange(CellRef startRef, CellRef endRef) {
      this.mStartRef = startRef;
      this.mEndRef = endRef;
   }

   public CellRef getStartRef() {
      return this.mStartRef;
   }

   public void setStartRef(CellRef startRef) {
      this.mStartRef = startRef;
   }

   public CellRef getEndRef() {
      return this.mEndRef;
   }

   public void setEndRef(CellRef endRef) {
      this.mEndRef = endRef;
   }

   public String toString() {
      StringBuilder sb = (new StringBuilder()).append(" startRef=").append(this.mStartRef).append(" endRef=").append(this.mEndRef);
      return sb.toString();
   }

   public boolean isSingleCell() {
      return false;
   }

   public int getWidth() {
      return this.getAbsoluteEndColumn(0) - this.getAbsoluteStartColumn(0) + 1;
   }

   public int getDepth() {
      return this.getAbsoluteEndRow(0) - this.getAbsoluteStartRow(0) + 1;
   }

   public int getAbsoluteStartRow(int rowOrigin) {
      return Math.min(this.mStartRef.getAbsoluteRow(rowOrigin), this.mEndRef.getAbsoluteRow(rowOrigin));
   }

   public int getAbsoluteEndRow(int rowOrigin) {
      return Math.max(this.mStartRef.getAbsoluteRow(rowOrigin), this.mEndRef.getAbsoluteRow(rowOrigin));
   }

   public int getAbsoluteStartColumn(int columnOrigin) {
      return Math.min(this.mStartRef.getAbsoluteColumn(columnOrigin), this.mEndRef.getAbsoluteColumn(columnOrigin));
   }

   public int getAbsoluteEndColumn(int columnOrigin) {
      return Math.max(this.mStartRef.getAbsoluteColumn(columnOrigin), this.mEndRef.getAbsoluteColumn(columnOrigin));
   }

   public Worksheet getWorksheet(Worksheet owningSheet) {
      return this.mStartRef.getWorksheet(owningSheet);
   }

   public boolean renameWorksheetReference(String oldName, String newName) {
      boolean result = false;
      if(this.mStartRef != null && this.mStartRef.renameWorksheetReference(oldName, newName)) {
         result = true;
      }

      if(this.mEndRef != null && this.mEndRef.renameWorksheetReference(oldName, newName)) {
         result = true;
      }

      return result;
   }

   public boolean insertColumn(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      boolean result = false;
      if(this.mStartRef != null && this.mStartRef.insertColumn(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount)) {
         result = true;
      }

      if(this.mEndRef != null && this.mEndRef.insertColumn(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount)) {
         result = true;
      }

      return result;
   }

   public boolean isInsertColumnRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      return this.mStartRef.isInsertColumnRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount) || this.mEndRef.isInsertColumnRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount);
   }

   public boolean insertRow(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      boolean result = false;
      if(this.mStartRef != null && this.mStartRef.insertRow(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount)) {
         result = true;
      }

      if(this.mEndRef != null && this.mEndRef.insertColumn(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount)) {
         result = true;
      }

      return result;
   }

   public boolean isInsertRowRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      return this.mStartRef.isInsertRowRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount) || this.mEndRef.isInsertRowRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount);
   }

   public boolean removeColumn(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      boolean result = false;
      if(this.mStartRef != null && this.mStartRef.removeColumn(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount)) {
         result = true;
      }

      if(this.mEndRef != null && this.mEndRef.removeColumn(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount)) {
         result = true;
      }

      return result;
   }

   public boolean isRemoveColumnRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int columnIndex, int columnCount) {
      return this.mStartRef.isRemoveColumnRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount) || this.mEndRef.isRemoveColumnRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, columnIndex, columnCount);
   }

   public boolean removeRow(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      boolean result = false;
      if(this.mStartRef != null && this.mStartRef.removeRow(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount)) {
         result = true;
      }

      if(this.mEndRef != null && this.mEndRef.removeRow(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount)) {
         result = true;
      }

      return result;
   }

   public boolean isRemoveRowRelevent(Worksheet owningWorksheet, int originRow, int originColumn, Worksheet targetWorksheet, int rowIndex, int rowCount) {
      return this.mStartRef.isRemoveRowRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount) || this.mEndRef.isRemoveRowRelevent(owningWorksheet, originRow, originColumn, targetWorksheet, rowIndex, rowCount);
   }

   public int hashCode() {
      return (this.mStartRef != null?this.mStartRef.hashCode():0) + (this.mEndRef != null?this.mEndRef.hashCode():0);
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(!(obj instanceof CellRange)) {
         return super.equals(obj);
      } else {
         CellRange other = (CellRange)obj;
         return this.mStartRef == other.mStartRef || this.mStartRef != null && other.mStartRef != null && this.mStartRef.equals(other.mStartRef) && this.mEndRef == other.mEndRef || this.mEndRef != null && other.mEndRef != null && this.mEndRef.equals(other.mEndRef);
      }
   }

   public Object clone() throws CloneNotSupportedException {
      CellRange copy = (CellRange)super.clone();
      copy.mStartRef = (CellRef)this.mStartRef.clone();
      copy.mEndRef = (CellRef)this.mEndRef.clone();
      return copy;
   }

   public boolean isVector() {
      int depth = this.getDepth();
      int width = this.getWidth();
      return depth == 1 && width >= 1 || depth >= 1 && width == 1;
   }
}
