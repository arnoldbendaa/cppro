// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.awt.table.CellSpanModel;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.WorksheetListener;
import com.cedar.cp.util.flatform.model.event.WorksheetColumnEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetRowEvent;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.sparse.Sparse2DArrayEvent;
import com.cedar.cp.util.sparse.Sparse2DArrayListener;

public class SparseTableModel extends AbstractTableModel implements Sparse2DArrayListener, CellSpanModel, WorksheetListener {
	private static final long serialVersionUID = 5360391115318842956L;
   
   private int mNumRows;
   private int mNumColumns;
   private Worksheet mWorksheet;
   private CellFactory mCellFactory;
   private boolean mReadOnly;
   private Set<Integer> hiddenRows=new HashSet<Integer>();


   public SparseTableModel(Worksheet worksheet, int rows, int columns, CellFactory factory, boolean readOnly) {
      this.mWorksheet = worksheet;
      this.mNumRows = rows;
      if(this.mNumColumns >= this.mWorksheet.getMaxColumns()) {
         throw new IllegalArgumentException("Max number of columns is " + this.mWorksheet.getMaxColumns());
      } else {
         this.mNumColumns = columns;
         this.mCellFactory = factory;
         this.mReadOnly = readOnly;
         this.mWorksheet.addSparse2DArrayListener(this);
         this.mWorksheet.addWorksheetListener(this);
      }
   }

//   /**
//    * Verifies if specified row is hidden
//    * @param rowNumber
//    * @return
//    */
//   public boolean isRowHidden(Integer rowNumber){
//	   return hiddenRows.contains(rowNumber);
//   }
   
   public int getRowCount() {
      return this.mNumRows;
   }

   public int getColumnCount() {
      return this.mNumColumns;
   }

   public String getColumnName(int column) {
      return this.mWorksheet.getColumnName(column);
   }

   public Class getColumnClass(int column) {
      return Cell.class;
   }

   public boolean isCellEditable(int rowIndex, int columnIndex) {
      if(this.mReadOnly) {
         return false;
      } else if(this.mWorksheet != null && this.mWorksheet.isDesignMode()) {
         return true;
      } else {
         Cell c = this.getValueAt(rowIndex, columnIndex);
         CellFormat cellFormat;
         if(c != null) {
            cellFormat = c.getFormat();
            return !cellFormat.isLocked();
         } else {
            cellFormat = this.mWorksheet.getFormat(rowIndex, columnIndex, rowIndex, columnIndex);
            return cellFormat != null && !cellFormat.isLocked();
         }
      }
   }

   public Cell getValueAt(int rowIndex, int columnIndex) {
      Cell c = (Cell)this.mWorksheet.get(rowIndex, columnIndex);
      if(c != null && this.mWorksheet != null && !this.mWorksheet.isDesignMode()) {
         CellFormat format = c.getFormat();
         if(format.isHidden()) {
            return null;
         }
      }

      return c;
   }

   public void validateCellText(int rowIndex, int columnIndex, String value) throws ParseException {
      this.mWorksheet.validateCellText(rowIndex, columnIndex, value);
   }

   public void setValueAt(Object value, int rowIndex, int columnIndex) {
      if(value instanceof String) {
         Cell cell = (Cell)this.mWorksheet.get(rowIndex, columnIndex);
         if(cell == null) {
            this.mCellFactory.createCell(rowIndex, columnIndex);
         }

         try {
            this.mWorksheet.setCellValue((String)value, rowIndex, columnIndex);
         } catch (ParseException var6) {
            var6.printStackTrace();
            throw new IllegalStateException("Rejected model update for cell:" + var6.getMessage() + " value:" + value);
         } catch (Throwable var7) {
            var7.printStackTrace();
            throw new IllegalStateException("Rejected model update for cell:" + var7.getMessage() + " value:" + value);
         }
      } else if(value instanceof Cell) {
         this.mWorksheet.put(rowIndex, columnIndex, (Cell)value);
      }

   }

   public Cell newCell(int row, int column) {
      return this.mWorksheet.newCell(row, column);
   }

   public void sparse2DArrayChanged(Sparse2DArrayEvent e) {
      this.fireTableChanged(new TableModelEvent(this, e.getFirstRow(), e.getLastRow(), e.getColumn(), e.getType()));
   }

   public Worksheet getWorksheet() {
      return this.mWorksheet;
   }

   public void insertColumn(int columnIndex) {
      this.mWorksheet.insertColumns(columnIndex, 1);
   }

   public void deleteColumn(int columnIndex) {
      this.mWorksheet.removeColumns(columnIndex, 1);
   }

   public void insertRow(int rowIndex) {
      this.mWorksheet.insertRows(rowIndex, 1);
   }

   public void deleteRow(int rowIndex) {
      this.mWorksheet.removeRows(rowIndex, 1);
   }

   public void worksheetStructureChange(WorksheetEvent e) {}

   public void worksheetStructureChanged(WorksheetEvent e) {
      if(e instanceof WorksheetRowEvent) {
         if(((WorksheetRowEvent)e).getType() == 0) {
            this.fireTableRowsInserted(((WorksheetRowEvent)e).getRow(), ((WorksheetRowEvent)e).getRowCount());
         } else {
            this.fireTableRowsDeleted(((WorksheetRowEvent)e).getRow(), ((WorksheetRowEvent)e).getRowCount());
         }
      } else if(e instanceof WorksheetColumnEvent) {
         ;
      }

   }

   public void worksheetFormatChanged(WorksheetFormatEvent e) {
      for(int col = e.getStartColumn(); col <= e.getEndColumn(); ++col) {
         this.fireTableChanged(new TableModelEvent(this, e.getStartRow(), e.getEndRow() + 1, col, 0));
      }

   }

   public RTree.Rect getCellRect(int row, int column) {
      return this.getWorksheet().queryCellMergeRect(row, column);
   }

//	public Set<Integer> getHiddenRows() {
//		return hiddenRows;
//	}
}
