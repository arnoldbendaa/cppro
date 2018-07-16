// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.sparse.Sparse2DArrayEvent;
import com.cedar.cp.util.sparse.Sparse2DArrayListener;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.AbstractDataset;

public class WorksheetDataset extends AbstractDataset implements CategoryDataset, Sparse2DArrayListener, XMLWritable {

   private Worksheet mWorksheet;
   private int mStartRow = -1;
   private int mStartColumn = -1;
   private int mEndRow = -1;
   private int mEndColumn = -1;
   private int mOffset;
   private String[] mPointNames;
   private String[] mSeriesNames;


   public WorksheetDataset() {}

   public WorksheetDataset(Worksheet model, int startRow, int startColumn, int endRow, int endColumn) {
      this.mWorksheet = model;
      this.mStartRow = startRow;
      this.mStartColumn = startColumn;
      this.mEndRow = endRow;
      this.mEndColumn = endColumn;
      this.createInternal();
   }

   private void createInternal() {
      if(this.mWorksheet != null && this.mStartRow >= 0 && this.mStartColumn >= 0 && this.mEndRow >= 0 && this.mEndColumn >= 0) {
         int nColumns = this.mEndColumn - this.mStartColumn + 1;
         int nRows = this.mEndRow - this.mStartRow + 1;
         this.mOffset = 0;
         Cell value = (Cell)this.mWorksheet.get(this.mStartRow, this.mStartColumn);
         if(value != null && value.getNumericValue() != null) {
            this.mPointNames = new String[nColumns];
            this.mSeriesNames = new String[nRows];

            int i;
            for(i = 0; i < nColumns; ++i) {
               this.mPointNames[i] = "Point " + (i + 1);
            }

            for(i = 0; i < nRows; ++i) {
               this.mSeriesNames[i] = "Series " + (i + 1);
            }
         } else {
            if(nRows <= 1 || nColumns <= 1) {
               return;
            }

            this.extractLabels();
         }

         this.fireDatasetChanged();
         this.mWorksheet.addSparse2DArrayListener(this);
      }
   }

   private void extractLabels() {
      int nColumns = this.mEndColumn - this.mStartColumn + 1;
      int nRows = this.mEndRow - this.mStartRow + 1;
      this.mPointNames = new String[nColumns - 1];
      this.mSeriesNames = new String[nRows - 1];
      this.mOffset = 1;

      int i;
      Cell text;
      for(i = 1; i < nColumns; ++i) {
         text = (Cell)this.mWorksheet.get(this.mStartRow, this.mStartColumn + i);
         this.mPointNames[i - 1] = text != null?text.getCellText():"Point " + (i + 1);
      }

      for(i = 1; i < nRows; ++i) {
         text = (Cell)this.mWorksheet.get(this.mStartRow + i, this.mStartColumn);
         this.mSeriesNames[i - 1] = text != null?text.getCellText():"Series " + (i + 1);
      }

   }

   public Comparable getRowKey(int i) {
      return this.mSeriesNames[i];
   }

   public int getRowIndex(Comparable comparable) {
      for(int i = 0; i < this.mSeriesNames.length; ++i) {
         if(comparable.compareTo(this.mSeriesNames[i]) == 0) {
            return i;
         }
      }

      return -1;
   }

   public List getRowKeys() {
      return Arrays.asList(this.mSeriesNames);
   }

   public Comparable getColumnKey(int i) {
      return this.mPointNames[i];
   }

   public int getColumnIndex(Comparable comparable) {
      for(int i = 0; i < this.mPointNames.length; ++i) {
         if(comparable.compareTo(this.mPointNames[i]) == 0) {
            return i;
         }
      }

      return -1;
   }

   public List getColumnKeys() {
      return Arrays.asList(this.mPointNames);
   }

   public Number getValue(Comparable comparable, Comparable comparable1) {
      int i = this.getRowIndex(comparable);
      int i1 = this.getColumnIndex(comparable1);
      return this.getValue(i, i1);
   }

   public int getRowCount() {
      return this.mSeriesNames.length;
   }

   public int getColumnCount() {
      return this.mPointNames.length;
   }

   public Number getValue(int i, int i1) {
      Cell cell = (Cell)this.mWorksheet.get(this.mStartRow + this.mOffset + i, this.mStartColumn + this.mOffset + i1);
      if(cell != null) {
         Object numericValue = cell.getNumericValue();
         if(numericValue instanceof Number) {
            return (Number)numericValue;
         }
      }

      return null;
   }

   public int getStartRow() {
      return this.mStartRow;
   }

   public void setStartRow(int startRow) {
      this.mStartRow = startRow;
      this.createInternal();
   }

   public int getStartColumn() {
      return this.mStartColumn;
   }

   public void setStartColumn(int startColumn) {
      this.mStartColumn = startColumn;
      this.createInternal();
   }

   public int getEndRow() {
      return this.mEndRow;
   }

   public void setEndRow(int endRow) {
      this.mEndRow = endRow;
      this.createInternal();
   }

   public int getEndColumn() {
      return this.mEndColumn;
   }

   public void setEndColumn(int endColumn) {
      this.mEndColumn = endColumn;
      this.createInternal();
   }

   public Worksheet getWorksheet() {
      return this.mWorksheet;
   }

   public void setWorksheet(Worksheet worksheet) {
      this.mWorksheet = worksheet;
      this.createInternal();
   }

   public void sparse2DArrayChanged(Sparse2DArrayEvent e) {
      int row = e.getFirstRow();
      int column = e.getColumn();
      if(this.mOffset > 0 && (this.mStartRow == row || this.mStartColumn == column)) {
         this.extractLabels();
      }

      if(this.mStartRow <= row && row <= this.mEndRow && this.mStartColumn <= column && column <= this.mEndColumn) {
         this.fireDatasetChanged();
      }

   }

   public void writeXml(Writer out) throws IOException {
      out.write("<WorksheetDataset startRow=\"" + this.mStartRow + "\" startColumn=\"" + this.mStartColumn + "\" endRow=\"" + this.mEndRow + "\" endColumn=\"" + this.mEndColumn + "\" />");
   }
}
