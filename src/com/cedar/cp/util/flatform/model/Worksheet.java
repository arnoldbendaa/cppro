// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.event.EventListenerList;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.clipboard.CellData;
import com.cedar.cp.util.flatform.model.clipboard.CellSelection;
import com.cedar.cp.util.flatform.model.event.WorksheetColumnEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetRowEvent;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.CompositeCellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.Formula;
import com.cedar.cp.util.flatform.model.parser.FormulaEngine;
import com.cedar.cp.util.flatform.model.parser.WorksheetCellRef;
import com.cedar.cp.util.flatform.model.undo.CellEdit;
import com.cedar.cp.util.flatform.model.undo.FormatEdit;
import com.cedar.cp.util.flatform.model.undo.GroupCellsEdit;
import com.cedar.cp.util.flatform.model.undo.InsertColumnsEdit;
import com.cedar.cp.util.flatform.model.undo.InsertRowsEdit;
import com.cedar.cp.util.flatform.model.undo.RemoveColumnsEdit;
import com.cedar.cp.util.flatform.model.undo.RemoveRowsEdit;
import com.cedar.cp.util.flatform.model.undo.SplitCellsEdit;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import com.cedar.cp.util.xmlform.XMLWritable;

public class Worksheet extends LinkedListSparse2DArray<Cell> implements WorksheetColumnMapping, XMLWritable {

   private int mMaxRows = 1024;
   private transient EventListenerList mEventListenerList;
   private int mViewLayer = 0;
   private String mName;
   private boolean mShowGrid = true;
   private boolean mHidden = false;
   private Workbook mWorkbook;
   private RTree<CellFormatGroup> mCellFormats = new RTree(10);
   private RTree<RTree.Rect> mMergedCells = new RTree(10);
   private RTree<Cell> mCellRectTree = new RTree(10);
   private Map<Integer, ColumnFormat> mColumnFormats = new HashMap();
   private Map<String, Object> mOriginalCellValueMap = new HashMap();
   private int mCellFormatVersion;
   public static String[] sColumnHeadings = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ", "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX", "HY", "HZ", "IA", "IB", "IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP", "IQ", "IR", "IS", "IT", "IU", "IV", "IW", "IX", "IY", "IZ"};
   private static Map<String, Integer> sColumnMap = new HashMap(sColumnHeadings.length);
   private Properties mProperties;
   public static final int VIEW_LAYER_VALUES = 0;
   public static final int VIEW_LAYER_FORMULAE = 1;
   public static final int VIEW_LAYER_INPUT_MAPPINGS = 2;
   public static final int VIEW_LAYER_OUTPUT_MAPPINGS = 3;
   
   private boolean isValid = true;


   public Worksheet() {
      this.mName = "Sheet";
   }

   public Worksheet(String name) {
      this.mName = name;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public boolean isShowGrid() {
      return this.mShowGrid;
   }

   public void setShowGrid(boolean showGrid) {
      this.mShowGrid = showGrid;
   }

   public boolean isHidden() {
      return this.mHidden;
   }

   public void setHidden(boolean hidden) {
      this.mHidden = hidden;
   }

   public void addCell(Cell cell) {
      cell.setWorksheet(this);
      this.put(cell.getRow(), cell.getColumn(), cell);
   }

   public void setCellText(Cell cell, String value) {
      cell.setText(value);
   }

   public Collection<ColumnFormat> getColumnFormats() {
      return this.mColumnFormats.values();
   }

   public void setColumnFormats(Map<Integer, ColumnFormat> columnFormats) {
      this.mColumnFormats = columnFormats;
   }

   public void addColumnFormat(ColumnFormat column) {
      Integer index = Integer.valueOf(column.getColumn());
      this.mColumnFormats.put(index, column);
   }

   public void clearColumnFormats() {
      this.mColumnFormats.clear();
   }

   public int[] getCellCoordinates(String reference) throws ParseException {
      CellRangeRef cellRangeRef = this.getFormulaEngine().parseCellRef(this, reference, 0, 0);
      return new int[]{cellRangeRef.getAbsoluteStartRow(0), cellRangeRef.getAbsoluteEndColumn(0)};
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<worksheet ");
      XmlUtils.outputAttribute(out, "name", this.mName);
      XmlUtils.outputAttribute(out, "showGrid", Boolean.valueOf(this.mShowGrid));
      XmlUtils.outputAttribute(out, "hidden", Boolean.valueOf(this.mHidden));
      XmlUtils.outputAttribute(out, "cellFormatVersion", Integer.valueOf(this.mCellFormatVersion));
      out.write(" >");
      if(this.mProperties != null) {
         this.mProperties.writeXml(out);
      }

      Iterator iter = this.iterator();

      while(iter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellFormatGroups = (LinkedListSparse2DArray.CellLink)iter.next();
         Cell mergedCellsIter = (Cell)cellFormatGroups.getData();
         mergedCellsIter.writeXml(out);
      }

      Iterator cellFormatGroups1 = this.mColumnFormats.values().iterator();

      while(cellFormatGroups1.hasNext()) {
         ColumnFormat mergedCellsIter1 = (ColumnFormat)cellFormatGroups1.next();
         mergedCellsIter1.writeXml(out);
      }

      this.reapCellFormats();
      cellFormatGroups1 = this.mCellFormats.interator();

      while(cellFormatGroups1.hasNext()) {
         CellFormatGroup mergedCellsIter2 = (CellFormatGroup)cellFormatGroups1.next();
         mergedCellsIter2.writeXml(out);
      }

      Iterator mergedCellsIter3 = this.mMergedCells.interator();

      while(mergedCellsIter3.hasNext()) {
         RTree.Rect mergeRect = (RTree.Rect)mergedCellsIter3.next();
         out.write("<mergeRect ");
         XmlUtils.outputAttribute(out, "startRow", Integer.valueOf(mergeRect.mStartRow));
         XmlUtils.outputAttribute(out, "startColumn", Integer.valueOf(mergeRect.mStartColumn));
         XmlUtils.outputAttribute(out, "endRow", Integer.valueOf(mergeRect.mEndRow));
         XmlUtils.outputAttribute(out, "endColumn", Integer.valueOf(mergeRect.mEndColumn));
         out.write("/>");
      }

      out.write("</worksheet>");
   }

   public int getColumn(String columnName) {
      Integer columnNumber = (Integer)sColumnMap.get(columnName);
      return columnNumber != null?columnNumber.intValue():-1;
   }

   public int getMaxColumns() {
      return sColumnHeadings.length;
   }

   public void setMaxRows(int maxRows) {
      this.mMaxRows = maxRows;
   }

   public int getMaxRows() {
      return this.mMaxRows;
   }

   public String getColumnName(int column) {
      return column < sColumnHeadings.length && column >= 0?sColumnHeadings[column]:null;
   }

   public Workbook getWorkbook() {
      return this.mWorkbook;
   }

   public void setWorkbook(Workbook workbook) {
      this.mWorkbook = workbook;
   }

   public boolean isDesignMode() {
      return this.mWorkbook != null && this.mWorkbook.isDesignMode();
   }

   public void initAfterLoad() {
      long t = System.currentTimeMillis();
      Iterator cellIter = this.iterator();

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)this.get(cellLink.getRow(), cellLink.getColumn());
         if(cell != null && this.getFormulaEngine().isFormula(cell.getText())) {
            try {
               this.updateCellFormula(cell, cell.getText());
            } catch (ParseException var7) {
               throw new RuntimeException("Failed to compile formula:" + cell.getText() + "\n context:\n" + var7.getMessage(), var7);
            }
         } else if(cell != null && cell.getCPChartFactory() != null) {
            cell.createInternalChart();
         } else if(cell != null && cell.getCPImageFactory() != null) {
            cell.createInternalImage();
         }
      }

   }

   public void validateCellText(int rowIndex, int columnIndex, String value) throws ParseException {
      if(this.getFormulaEngine().isFormula(value)) {
         this.getFormulaEngine().compile(this, value, rowIndex, columnIndex);
      }

   }

   public void updateCellFormula(Cell cell, String value) throws ParseException {
      String normalisedFormula = this.getFormulaEngine().compile(this, value, cell.getRow(), cell.getColumn());
      if(cell.getFormula() != null) {
         this.removeImmediateDependencies(cell);
         this.getWorkbook().deregisterFormulaCell(cell);
      }

      cell.setFormulaText(value);
      Formula formula = this.getFormulaEngine().getFormula(normalisedFormula);
      if(formula == null) {
         formula = new Formula();
         formula.setFormula(normalisedFormula);
         formula.setOriginFormulaText(value);
         formula.setOriginRow(cell.getRow());
         formula.setOriginColumn(cell.getColumn());
         formula.setOriginWorksheet(this.getName());
         formula.setParseTree(this.getFormulaEngine().getFormulaAST());
         formula.setRefs(new HashSet(this.getFormulaEngine().getRefs()));
         formula.setRefOffsets(new ArrayList(this.getFormulaEngine().getRefOffsets()));
         this.getFormulaEngine().setFormula(normalisedFormula, formula);
      }

      cell.setFormula(formula);
      this.createImmediateDependencies(cell);
      this.getWorkbook().registerFormulaCell(cell);
   }

   public void queryDependencyTreeRootCells(List<Cell> roots) {
      Iterator cellIter = this.iterator();

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)this.get(cellLink.getRow(), cellLink.getColumn());
         if(cell != null && cell.isDependencyTreeRoot()) {
            roots.add(cell);
         }
      }

   }

   public Cell newCell(int row, int column) {
      Cell cell = new Cell(this, row, column);
      Set referenceCells = this.getWorkbook().queryCellReferences(cell);
      if(!referenceCells.isEmpty()) {
         Iterator i$ = referenceCells.iterator();

         while(i$.hasNext()) {
            Cell refCell = (Cell)i$.next();
            if(cell.getRefs() == null) {
               cell.setRefs(new HashSet());
            }

            if(refCell.getWorksheet() != this) {
               cell.getRefs().add(new WorksheetCellRef(refCell.getWorksheet().getName(), refCell.getColumn() - column, false, refCell.getRow() - row, false));
            } else {
               cell.getRefs().add(new CellRef(refCell.getColumn() - column, false, refCell.getRow() - row, false));
            }
         }
      }

      this.put(row, column, cell);
      return cell;
   }

   public void setCellValue(String value, int row, int column) throws ParseException {
      String originalCellText = this.doSetCellValue(value, row, column);
      this.getWorkbook().postSingleUndoableEdit(new CellEdit(this, this.mViewLayer, row, column, originalCellText, value));
   }

   public void setCellValue(Object value, int row, int column) throws ParseException {
      this.doSetCellValue(value, row, column);
   }

   public String doSetCellValue(Object value, int row, int column) throws ParseException {
      String originalCellText = null;
      Cell cell = (Cell)this.get(row, column);
      if(cell == null) {
         cell = this.newCell(row, column);
      }

      cell.setValue((Object)null);
      if(value != null && !(value instanceof String)) {
         cell.setValue(value);
      } else {
         String stringValue = (String)value;
         if(this.mViewLayer == 2) {
            originalCellText = cell.getInputMapping();
            if(stringValue != null && stringValue.length() == 0) {
               stringValue = null;
            }

            cell.setInputMapping(stringValue);
         } else if(this.mViewLayer == 3) {
            originalCellText = cell.getOutputMapping();
            if(stringValue != null && stringValue.length() == 0) {
               stringValue = null;
            }

            cell.setOutputMapping(stringValue);
         } else {
            originalCellText = cell.getText();
            if(this.getFormulaEngine().isFormula(stringValue)) {
               if(StringUtils.differ(stringValue, cell.getText())) {
                  this.updateCellFormula(cell, stringValue);
               }
            } else {
               if(cell.getFormula() != null) {
                  this.removeImmediateDependencies(cell);
                  this.getWorkbook().deregisterFormulaCell(cell);
                  cell.setFormula((Formula)null);
                  cell.setValue((Object)null);
               }

               cell.setText(stringValue);
            }

            this.getWorkbook().recalcDependencyTree(cell);
         }
      }

      this.fireSparse2DArrayCellUpdated(row, column);
      return originalCellText;
   }

   private void createImmediateDependencies(Cell cell) {
      if(cell != null && cell.getFormula() != null) {
         Formula formula = cell.getFormula();
         Iterator i$ = formula.getRefs().iterator();

         while(i$.hasNext()) {
            CellRangeRef cellRangeRef = (CellRangeRef)i$.next();
            if(cellRangeRef.isSingleCell()) {
               CellRef originRow1 = cellRangeRef.getStartRef();
               Cell originColumn1 = originRow1.getCell(this, cell.getRow(), cell.getColumn());
               if(originColumn1 != null) {
                  CellRef targetSheet1 = originRow1.inverseReference(this, cell.getRow(), cell.getColumn());
                  if(originColumn1.getRefs() == null) {
                     originColumn1.setRefs(new HashSet());
                  }

                  originColumn1.getRefs().add(targetSheet1);
               }
            } else {
               int originRow = cell.getRow();
               int originColumn = cell.getColumn();
               Worksheet targetSheet = cellRangeRef.getStartRef().getWorksheet(this);
               if(targetSheet != null) {
                  Iterator cellIter = targetSheet.rangeIterator(cellRangeRef.getAbsoluteStartRow(originRow), cellRangeRef.getAbsoluteStartColumn(originColumn), cellRangeRef.getAbsoluteEndRow(originRow), cellRangeRef.getAbsoluteEndColumn(originColumn));

                  while(cellIter.hasNext()) {
                     LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
                     Cell refCell = (Cell)cellLink.getData();
                     if(refCell != null) {
                        if(refCell.getRefs() == null) {
                           refCell.setRefs(new HashSet());
                        }

                        CellRef inverseRef;
                        if(targetSheet == this) {
                           inverseRef = new CellRef(originColumn - refCell.getColumn(), originRow - refCell.getRow());
                        } else {
                           inverseRef = new WorksheetCellRef(this.getName(), originColumn - refCell.getColumn(), originRow - refCell.getRow());
                        }

                        refCell.getRefs().add(inverseRef);
                     }
                  }
               }
            }
         }
      }

   }

   private void removeImmediateDependencies(Cell cell) {
      if(cell != null && cell.getFormula() != null) {
         Formula formula = cell.getFormula();
         Iterator i$ = formula.getRefs().iterator();

         while(i$.hasNext()) {
            CellRangeRef cellRangeRef = (CellRangeRef)i$.next();
            if(cellRangeRef.isSingleCell()) {
               CellRef originRow1 = cellRangeRef.getStartRef();
               Cell originColumn1 = originRow1.getCell(this, cell.getRow(), cell.getColumn());
               if(originColumn1 != null) {
                  CellRef targetSheet1 = originRow1.inverseReference(this, cell.getRow(), cell.getColumn());
                  if(originColumn1.getRefs() != null) {
                     originColumn1.getRefs().remove(targetSheet1);
                  }
               }
            } else {
               int originRow = cell.getRow();
               int originColumn = cell.getColumn();
               Worksheet targetSheet = cellRangeRef.getStartRef().getWorksheet(this);
               if(targetSheet != null) {
                  Iterator cellIter = targetSheet.rangeIterator(cellRangeRef.getAbsoluteStartRow(originRow), cellRangeRef.getAbsoluteStartColumn(originColumn), cellRangeRef.getAbsoluteEndRow(originRow), cellRangeRef.getAbsoluteEndColumn(originColumn));

                  while(cellIter.hasNext()) {
                     LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
                     Cell refCell = (Cell)cellLink.getData();
                     if(refCell != null && refCell.getRefs() != null) {
                        if(targetSheet == this) {
                           refCell.getRefs().remove(new CellRef(originColumn - refCell.getColumn(), originRow - refCell.getRow()));
                        } else {
                           refCell.getRefs().remove(new WorksheetCellRef(this.getName(), originColumn - refCell.getColumn(), originRow - refCell.getRow()));
                        }
                     }
                  }
               }
            }
         }
      }

   }

   void postRemoveSheet() {
      Iterator sheetIter = this.iterator();

      while(sheetIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellsToRecalculate = (LinkedListSparse2DArray.CellLink)sheetIter.next();
         this.removeImmediateDependencies((Cell)cellsToRecalculate.getData());
         this.getWorkbook().deregisterFormulaCell((Cell)cellsToRecalculate.getData());
      }

      HashSet cellsToRecalculate1 = new HashSet();
      Iterator cellRectIterator = this.getCellRectTree().interator();

      while(cellRectIterator.hasNext()) {
         Cell i$ = (Cell)cellRectIterator.next();
         if(i$.getWorksheet() != this) {
            cellsToRecalculate1.add(i$);
         }
      }

      Iterator i$1 = cellsToRecalculate1.iterator();

      while(i$1.hasNext()) {
         Cell cell = (Cell)i$1.next();
         this.getWorkbook().recalcDependencyTree(cell);
      }

   }

   void rename(String newName) {
      List sheets = this.getWorkbook().getWorksheets();
      Iterator i$ = sheets.iterator();

      while(i$.hasNext()) {
         Worksheet worksheet = (Worksheet)i$.next();
         worksheet.renameWorksheetReference(this, newName);
      }

      this.getWorkbook().getFormulaEngine().renameSheetReference(this, newName);
      this.mName = newName;
   }

   private void renameWorksheetReference(Worksheet sheet, String newName) {
      String oldFormulaRefecenceString = '\'' + sheet.getName() + "\'!";
      String newFormulaReferenceString = '\'' + newName + "\'!";

      Cell cell;
      for(Iterator cellIter = this.iterator(); cellIter.hasNext(); cell.renameWorksheet(oldFormulaRefecenceString, newFormulaReferenceString)) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         cell = (Cell)cellLink.getData();
         if(cell.getRefs() != null) {
            Iterator i$ = cell.getRefs().iterator();

            while(i$.hasNext()) {
               CellRef cellRef = (CellRef)i$.next();
               cellRef.renameWorksheetReference(sheet.getName(), newName);
            }
         }
      }

   }

   public void insertColumns(int startColumnIndex, int numColumns) {
      this.doInsertColumns(startColumnIndex, numColumns);
      this.getWorkbook().postSingleUndoableEdit(new InsertColumnsEdit(this, startColumnIndex, numColumns));
   }

   public void doInsertColumns(int startColumnIndex, int numColumns) {
      if(numColumns <= 0) {
         throw new IllegalArgumentException("numColumns must be 1 or greater");
      } else {
         this.fireWorksheetStructureChange(true, true, startColumnIndex, numColumns);
         super.insertColumns(startColumnIndex, numColumns);
         Iterator cellIter = this.rangeIterator(0, startColumnIndex, this.getRowCount(), this.getColumnCount());

         while(cellIter.hasNext()) {
            LinkedListSparse2DArray.CellLink i$ = (LinkedListSparse2DArray.CellLink)cellIter.next();
            Cell worksheet = (Cell)i$.getData();
            worksheet.setColumn(worksheet.getColumn() + numColumns);
         }

         Iterator i$1 = this.getWorkbook().getWorksheets().iterator();

         while(i$1.hasNext()) {
            Worksheet worksheet1 = (Worksheet)i$1.next();
            worksheet1.postColumnInsert(this, startColumnIndex, numColumns);
         }

         this.fireWorksheetStructureChanged(true, true, startColumnIndex, numColumns);
      }
   }

   private void postColumnInsert(Worksheet targetWorksheet, int startColumnIndex, int numColumns) {
      Iterator cellIter = this.iterator();

      Iterator mergeRects;
      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink searchRect = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell formats = (Cell)searchRect.getData();
         if(formats.getRefs() != null) {
            mergeRects = formats.getRefs().iterator();

            while(mergeRects.hasNext()) {
               CellRef i$ = (CellRef)mergeRects.next();
               i$.insertColumn(formats.getWorksheet(), formats.getRow(), formats.getColumn(), targetWorksheet, startColumnIndex, numColumns);
            }
         }

         if(formats.isFormula()) {
            formats.postColumnInsert(targetWorksheet, startColumnIndex, numColumns);
         }
      }

      if(targetWorksheet == this) {
         RTree.Rect searchRect1 = new RTree.Rect(startColumnIndex, 0, this.getMaxColumns(), this.getMaxRows());
         Collection formats1 = this.mCellFormats.find(searchRect1, new ArrayList());

         RTree.Rect rect;
         CellFormatGroup i$2;
         for(mergeRects = formats1.iterator(); mergeRects.hasNext(); this.mCellFormats.add(i$2.getRect(), i$2)) {
            i$2 = (CellFormatGroup)mergeRects.next();
            rect = i$2.getRect();
            this.mCellFormats.remove(rect, i$2);
            if(rect.mStartColumn <= startColumnIndex) {
               rect.mEndColumn += numColumns;
            } else {
               rect.mStartColumn += numColumns;
               rect.mEndColumn += numColumns;
            }
         }

         Collection mergeRects1 = this.mMergedCells.find(searchRect1, new ArrayList());

         for(Iterator i$1 = mergeRects1.iterator(); i$1.hasNext(); this.mMergedCells.add(rect, rect)) {
            rect = (RTree.Rect)i$1.next();
            this.mMergedCells.remove(rect, rect);
            if(rect.mStartColumn <= startColumnIndex) {
               rect.mEndColumn += numColumns;
            } else {
               rect.mStartColumn += numColumns;
               rect.mEndColumn += numColumns;
            }
         }

         this.fireWorksheetFormatChanged(0, startColumnIndex, this.getMaxRows(), this.getMaxColumns());
      }

   }

   public void removeColumns(int startColumnIndex, int numColumns) {
      this.doRemoveColumns(startColumnIndex, numColumns);
      this.getWorkbook().postSingleUndoableEdit(new RemoveColumnsEdit(this, startColumnIndex, numColumns));
   }

   public void doRemoveColumns(int startColumnIndex, int numColumns) {
      if(numColumns <= 0) {
         throw new IllegalArgumentException("numColumns must be 1 or greater");
      } else {
         this.fireWorksheetStructureChange(true, false, startColumnIndex, numColumns);
         Iterator cellIter = this.rangeIterator(0, startColumnIndex, this.getRowCount(), startColumnIndex + (numColumns - 1));

         LinkedListSparse2DArray.CellLink i$;
         Cell worksheet;
         while(cellIter.hasNext()) {
            i$ = (LinkedListSparse2DArray.CellLink)cellIter.next();
            worksheet = (Cell)i$.getData();

            try {
               this.setCellValue("", worksheet.getRow(), worksheet.getColumn());
            } catch (ParseException var7) {
               throw new IllegalStateException("Blanking cell content threw ParseException:" + var7, var7);
            }
         }

         super.removeColumns(startColumnIndex, numColumns);
         cellIter = this.rangeIterator(0, startColumnIndex, this.getRowCount(), this.getColumnCount());

         while(cellIter.hasNext()) {
            i$ = (LinkedListSparse2DArray.CellLink)cellIter.next();
            worksheet = (Cell)i$.getData();
            worksheet.setColumn(worksheet.getColumn() - numColumns);
         }

         Iterator i$1 = this.getWorkbook().getWorksheets().iterator();

         while(i$1.hasNext()) {
            Worksheet worksheet1 = (Worksheet)i$1.next();
            worksheet1.postColumnRemoval(this, startColumnIndex, numColumns);
         }

         this.fireWorksheetStructureChanged(true, false, startColumnIndex, numColumns);
      }
   }

   private void postColumnRemoval(Worksheet targetWorksheet, int startColumnIndex, int numColumns) {
      Iterator cellIter = this.iterator();

      Iterator mergeRects;
      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink searchRect = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell formats = (Cell)searchRect.getData();
         if(formats.getRefs() != null) {
            mergeRects = formats.getRefs().iterator();

            while(mergeRects.hasNext()) {
               CellRef i$ = (CellRef)mergeRects.next();
               i$.removeColumn(formats.getWorksheet(), formats.getRow(), formats.getColumn(), targetWorksheet, startColumnIndex, numColumns);
            }
         }

         if(formats.isFormula()) {
            formats.postColumnRemoval(targetWorksheet, startColumnIndex, numColumns);
         }
      }

      if(targetWorksheet == this) {
         RTree.Rect searchRect1 = new RTree.Rect(startColumnIndex, 0, this.getMaxColumns(), this.getMaxRows());
         Collection formats1 = this.mCellFormats.find(searchRect1, new ArrayList());

         RTree.Rect rect;
         CellFormatGroup i$2;
         for(mergeRects = formats1.iterator(); mergeRects.hasNext(); this.mCellFormats.add(i$2.getRect(), i$2)) {
            i$2 = (CellFormatGroup)mergeRects.next();
            rect = i$2.getRect();
            this.mCellFormats.remove(rect, i$2);
            if(rect.mStartColumn <= startColumnIndex) {
               rect.mEndColumn -= numColumns;
            } else {
               rect.mStartColumn -= numColumns;
               rect.mEndColumn -= numColumns;
            }
         }

         Collection mergeRects1 = this.mMergedCells.find(searchRect1, new ArrayList());

         for(Iterator i$1 = mergeRects1.iterator(); i$1.hasNext(); this.mMergedCells.add(rect, rect)) {
            rect = (RTree.Rect)i$1.next();
            this.mMergedCells.remove(rect, rect);
            if(rect.mStartColumn <= startColumnIndex) {
               rect.mEndColumn -= numColumns;
            } else {
               rect.mStartColumn -= numColumns;
               rect.mEndColumn -= numColumns;
            }
         }

         this.fireWorksheetFormatChanged(0, startColumnIndex, this.getMaxRows(), this.getMaxColumns());
      }

   }

   public void insertRows(int startRowIndex, int numRows) {
      this.doInsertRows(startRowIndex, numRows);
      this.getWorkbook().postSingleUndoableEdit(new InsertRowsEdit(this, startRowIndex, numRows));
   }

   public void doInsertRows(int startRowIndex, int numRows) {
      if(numRows <= 0) {
         throw new IllegalArgumentException("numRows must be 1 or greater");
      } else {
         this.fireWorksheetStructureChange(false, true, startRowIndex, numRows);
         super.insertRows(startRowIndex, numRows);
         Iterator cellIter = this.rangeIterator(startRowIndex, 0, this.getRowCount(), this.getColumnCount());

         while(cellIter.hasNext()) {
            LinkedListSparse2DArray.CellLink i$ = (LinkedListSparse2DArray.CellLink)cellIter.next();
            Cell worksheet = (Cell)i$.getData();
            worksheet.setRow(worksheet.getRow() + numRows);
         }

         Iterator i$1 = this.getWorkbook().getWorksheets().iterator();

         while(i$1.hasNext()) {
            Worksheet worksheet1 = (Worksheet)i$1.next();
            worksheet1.postRowInsert(this, startRowIndex, numRows);
         }

         this.fireWorksheetStructureChanged(false, true, startRowIndex, numRows);
      }
   }

   private void postRowInsert(Worksheet targetWorksheet, int startRowIndex, int numRows) {
      Iterator cellIter = this.iterator();

      Iterator mergeRects;
      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink searchRect = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell formats = (Cell)searchRect.getData();
         if(formats.getRefs() != null) {
            mergeRects = formats.getRefs().iterator();

            while(mergeRects.hasNext()) {
               CellRef i$ = (CellRef)mergeRects.next();
               i$.insertRow(formats.getWorksheet(), formats.getRow(), formats.getColumn(), targetWorksheet, startRowIndex, numRows);
            }
         }

         if(formats.isFormula()) {
            formats.postRowInsert(targetWorksheet, startRowIndex, numRows);
         }
      }

      if(targetWorksheet == this) {
         RTree.Rect searchRect1 = new RTree.Rect(0, startRowIndex, this.getMaxColumns(), this.getMaxRows());
         Collection formats1 = this.mCellFormats.find(searchRect1, new ArrayList());

         RTree.Rect rect;
         CellFormatGroup i$2;
         for(mergeRects = formats1.iterator(); mergeRects.hasNext(); this.mCellFormats.add(i$2.getRect(), i$2)) {
            i$2 = (CellFormatGroup)mergeRects.next();
            rect = i$2.getRect();
            this.mCellFormats.remove(rect, i$2);
            if(rect.mStartRow <= startRowIndex) {
               rect.mEndRow += numRows;
            } else {
               rect.mStartRow += numRows;
               rect.mEndRow += numRows;
            }
         }

         Collection mergeRects1 = this.mMergedCells.find(searchRect1, new ArrayList());

         for(Iterator i$1 = mergeRects1.iterator(); i$1.hasNext(); this.mMergedCells.add(rect, rect)) {
            rect = (RTree.Rect)i$1.next();
            this.mMergedCells.remove(rect, rect);
            if(rect.mStartRow <= startRowIndex) {
               rect.mEndRow += numRows;
            } else {
               rect.mStartRow += numRows;
               rect.mEndRow += numRows;
            }
         }

         this.fireWorksheetFormatChanged(0, startRowIndex, this.getMaxRows(), this.getMaxColumns());
      }

   }

   public void removeRows(int startRowIndex, int numRows) {
      this.doRemoveRows(startRowIndex, numRows);
      this.getWorkbook().postSingleUndoableEdit(new RemoveRowsEdit(this, startRowIndex, numRows));
   }

   public void doRemoveRows(int startRowIndex, int numRows) {
      if(numRows <= 0) {
         throw new IllegalArgumentException("numRows must be 1 or greater");
      } else {
         this.fireWorksheetStructureChange(false, false, startRowIndex, numRows);
         Iterator cellIter = this.rangeIterator(startRowIndex, 0, startRowIndex + (numRows - 1), this.getColumnCount());

         LinkedListSparse2DArray.CellLink cellLink;
         Cell worksheet;
         while(cellIter.hasNext()) {
            cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
            worksheet = (Cell)cellLink.getData();

            try {
               this.setCellValue("", worksheet.getRow(), worksheet.getColumn());
            } catch (ParseException var7) {
               throw new IllegalStateException("Blanking cell content threw ParseException:" + var7, var7);
            }
         }

         super.removeRows(startRowIndex, numRows);
         cellIter = this.rangeIterator(startRowIndex, 0, this.getRowCount(), this.getColumnCount());

         while(cellIter.hasNext()) {
            cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
            worksheet = (Cell)cellLink.getData();
            worksheet.setRow(worksheet.getRow() - numRows);
         }

         Iterator worksheetIterator = this.getWorkbook().getWorksheets().iterator();

         while(worksheetIterator.hasNext()) {
            Worksheet worksheet1 = (Worksheet)worksheetIterator.next();
            worksheet1.postRowRemoval(this, startRowIndex, numRows);
         }

         this.fireWorksheetStructureChanged(false, false, startRowIndex, numRows);
      }
   }

   private void postRowRemoval(Worksheet targetWorksheet, int startRowIndex, int numRows) {
      Iterator cellIter = this.iterator();

      Iterator mergeRects;
      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink searchRect = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell formats = (Cell)searchRect.getData();
         if(formats.getRefs() != null) {
            mergeRects = formats.getRefs().iterator();

            while(mergeRects.hasNext()) {
               CellRef i$ = (CellRef)mergeRects.next();
               i$.removeRow(formats.getWorksheet(), formats.getRow(), formats.getColumn(), targetWorksheet, startRowIndex, numRows);
            }
         }

         if(formats.isFormula()) {
            formats.postRowRemoval(targetWorksheet, startRowIndex, numRows);
         }
      }

      if(targetWorksheet == this) {
         RTree.Rect searchRect1 = new RTree.Rect(0, startRowIndex, this.getMaxColumns(), this.getMaxRows());
         Collection formats1 = this.mCellFormats.find(searchRect1, new ArrayList());

         RTree.Rect rect;
         CellFormatGroup i$2;
         for(mergeRects = formats1.iterator(); mergeRects.hasNext(); this.mCellFormats.add(i$2.getRect(), i$2)) {
            i$2 = (CellFormatGroup)mergeRects.next();
            rect = i$2.getRect();
            this.mCellFormats.remove(rect, i$2);
            if(rect.mStartRow <= startRowIndex) {
               rect.mEndRow -= numRows;
            } else {
               rect.mStartRow -= numRows;
               rect.mEndRow -= numRows;
            }
         }

         Collection mergeRects1 = this.mMergedCells.find(searchRect1, new ArrayList());

         for(Iterator i$1 = mergeRects1.iterator(); i$1.hasNext(); this.mMergedCells.add(rect, rect)) {
            rect = (RTree.Rect)i$1.next();
            this.mMergedCells.remove(rect, rect);
            if(rect.mStartRow <= startRowIndex) {
               rect.mEndRow -= numRows;
            } else {
               rect.mStartRow -= numRows;
               rect.mEndRow -= numRows;
            }
         }

         this.fireWorksheetFormatChanged(startRowIndex, 0, this.getMaxRows(), this.getMaxColumns());
      }

   }

   private FormulaEngine getFormulaEngine() {
      return this.mWorkbook.getFormulaEngine();
   }

   public int getViewLayer() {
      return this.mViewLayer;
   }

   public void setViewLayer(int viewLayer) {
      this.mViewLayer = viewLayer;
   }

   public RTree<Cell> getCellRectTree() {
      return this.mCellRectTree;
   }

   public void addWorksheetListener(WorksheetListener l) {
      this.getEventListenerList().add(WorksheetListener.class, l);
   }

   public void removeWorksheetListener(WorksheetListener l) {
      this.getEventListenerList().remove(WorksheetListener.class, l);
   }

   protected void fireWorksheetStructureChange(boolean columns, boolean inserted, int index, int count) {
      WorksheetEvent worksheetEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorksheetListener.class) {
            if(worksheetEvent == null) {
               worksheetEvent = this.createWorksheetEvent(columns, inserted, index, count);
            }

            ((WorksheetListener)listeners[i + 1]).worksheetStructureChange(worksheetEvent);
         }
      }

   }

   protected void fireWorksheetStructureChanged(boolean columns, boolean inserted, int index, int count) {
      WorksheetEvent worksheetEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorksheetListener.class) {
            if(worksheetEvent == null) {
               worksheetEvent = this.createWorksheetEvent(columns, inserted, index, count);
            }

            ((WorksheetListener)listeners[i + 1]).worksheetStructureChanged(worksheetEvent);
         }
      }

   }

   private WorksheetEvent createWorksheetEvent(boolean columns, boolean inserted, int index, int count) {
      return (WorksheetEvent)(columns?new WorksheetColumnEvent(this, inserted?0:1, index, count):new WorksheetRowEvent(this, inserted?0:1, index, count));
   }

   protected void fireWorksheetFormatChanged(int startRow, int startColumn, int endRow, int endColumn) {
      WorksheetFormatEvent worksheetEvent = null;
      Object[] listeners = this.getEventListenerList().getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == WorksheetListener.class) {
            if(worksheetEvent == null) {
               worksheetEvent = new WorksheetFormatEvent(this, startRow, startColumn, endRow, endColumn);
            }

            ((WorksheetListener)listeners[i + 1]).worksheetFormatChanged(worksheetEvent);
         }
      }

   }

   private EventListenerList getEventListenerList() {
      if(this.mEventListenerList == null) {
         this.mEventListenerList = new EventListenerList();
      }

      return this.mEventListenerList;
   }

   public void setCellFormat(int startRow, int startColumn, int endRow, int endColumn, Collection<FormatProperty> props) {
      if(!props.isEmpty()) {
         this.doSetCellFormat(startRow, startColumn, endRow, endColumn, props);
         this.getWorkbook().postSingleUndoableEdit(new FormatEdit(this, new RTree.Rect(startColumn, startRow, endColumn, endRow), props, this.getLastCellFormatVersion()));
      }

   }

   public void doSetCellFormat(int startRow, int startColumn, int endRow, int endColumn, Collection<FormatProperty> props) {
      CellFormatGroup cfg = new CellFormatGroup(new RTree.Rect(startColumn, startRow, endColumn, endRow), props, this.getNextCellFormatVersion());
      this.mCellFormats.add(cfg.getRect(), cfg);
      this.resetCellFormats(startRow, startColumn, endRow, endColumn);
      this.fireWorksheetFormatChanged(startRow, startColumn, endRow, endColumn);
      this.getWorkbook().fireWorksheetFormatsSelection(this, startRow, startColumn, endRow, endColumn, props);
   }

   public void doRemoveCellFormat(RTree.Rect rect, int versionNum) {
      ArrayList results = new ArrayList();
      this.mCellFormats.find(rect, results);
      Iterator i$ = results.iterator();

      while(i$.hasNext()) {
         CellFormatGroup cfg = (CellFormatGroup)i$.next();
         if(cfg.getRect().equals(rect) && cfg.getVersion() == versionNum) {
            this.mCellFormats.remove(cfg.getRect(), cfg);
            this.resetCellFormats(rect.mStartRow, rect.mStartColumn, rect.mEndRow, rect.mEndColumn);
            this.fireWorksheetFormatChanged(rect.mStartRow, rect.mStartColumn, rect.mEndRow, rect.mEndColumn);
            break;
         }
      }

   }

   private void resetCellFormats(int startRow, int startColumn, int endRow, int endColumn) {
      Iterator cellLinkIter = this.rangeIterator(startRow, startColumn, endRow, endColumn);

      while(cellLinkIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellLinkIter.next();
         if(cellLink.getData() != null) {
            ((Cell)cellLink.getData()).resetCellFormat();
         }
      }

   }

   private void reapCellFormats() {
      ArrayList formatsToDelete = new ArrayList();
      ArrayList interscetingGroups = new ArrayList();
      Iterator cfi = this.mCellFormats.interator();

      while(cfi.hasNext()) {
         CellFormatGroup i$ = (CellFormatGroup)cfi.next();
         interscetingGroups.clear();
         this.mCellFormats.find(i$.getRect(), interscetingGroups);
         Iterator cfg = interscetingGroups.iterator();

         while(cfg.hasNext()) {
            CellFormatGroup icfg = (CellFormatGroup)cfg.next();
            if(icfg != i$ && icfg.getRect().inside(i$.getRect()) && i$.getVersion() < icfg.getVersion() && !formatsToDelete.contains(icfg)) {
               if(icfg.keySet().containsAll(i$.keySet())) {
                  formatsToDelete.add(i$);
                  break;
               }

               if(i$.isEmpty()) {
                  formatsToDelete.add(i$);
               } else {
                  Iterator i$1 = icfg.keySet().iterator();

                  String key;
                  while(i$1.hasNext()) {
                     key = (String)i$1.next();
                     if(i$.keySet().contains(key)) {
                        i$.remove(key);
                     }
                  }

                  if(i$.getRect().equals(icfg.getRect()) && Math.abs(i$.getVersion() - icfg.getVersion()) == 1 && i$.getVersion() < icfg.getVersion()) {
                     i$1 = i$.keySet().iterator();

                     while(i$1.hasNext()) {
                        key = (String)i$1.next();
                        if(!icfg.keySet().contains(key)) {
                           FormatProperty property = (FormatProperty)i$.get(key);
                           icfg.put(property.getName(), property);
                        }
                     }

                     formatsToDelete.add(i$);
                  }
               }
            }
         }
      }

      Iterator i$2 = formatsToDelete.iterator();

      while(i$2.hasNext()) {
         CellFormatGroup cfg1 = (CellFormatGroup)i$2.next();
         this.mCellFormats.remove(cfg1.getRect(), cfg1);
      }

   }

   public CellFormat getFormat(int startRow, int startColumn, int endRow, int endColumn) {
      ArrayList formats = new ArrayList();
      this.mCellFormats.find(new RTree.Rect(startColumn, startRow, endColumn, endRow), formats);
      CompositeCellFormat result = null;
      if(!formats.isEmpty()) {
         result = new CompositeCellFormat();
         Collections.sort(formats, result);
         Iterator i$ = formats.iterator();

         while(i$.hasNext()) {
            CellFormatGroup cfg = (CellFormatGroup)i$.next();
            Iterator i$1 = cfg.values().iterator();

            while(i$1.hasNext()) {
               FormatProperty formatProperty = (FormatProperty)i$1.next();
               formatProperty.updateFormat(result);
            }
         }
      }

      return result == null?Cell.getDefaultFormat():result;
   }

   public Map<String, Collection<CellFormatEntry>> queryFormatProperties(int startRow, int startColumn, int endRow, int endColumn) {
      ArrayList formats = new ArrayList();
      this.mCellFormats.find(new RTree.Rect(startColumn, startRow, endColumn, endRow), formats);
      HashMap result = new HashMap();
      if(!formats.isEmpty()) {
         Collections.sort(formats, new CompositeCellFormat());
         Iterator i$ = formats.iterator();

         while(i$.hasNext()) {
            CellFormatGroup mapEntry = (CellFormatGroup)i$.next();
            RTree.Rect rectFragment = RTree.Rect.intersection(new RTree.Rect(startColumn, startRow, endColumn, endRow), mapEntry.getRect(), new RTree.Rect());

            FormatProperty formatProperty;
            Object entries;
            for(Iterator i$1 = mapEntry.values().iterator(); i$1.hasNext(); ((Collection)entries).add(new CellFormatEntry(formatProperty, rectFragment, (long)mapEntry.getVersion()))) {
               formatProperty = (FormatProperty)i$1.next();
               entries = (Collection)result.get(formatProperty.getName());
               if(entries == null) {
                  entries = new ArrayList();
                  FormatProperty defaultFormat = Cell.getDefaultFormat().getDefault(formatProperty.getName());
                  if(defaultFormat != null) {
                     ((Collection)entries).add(new CellFormatEntry(defaultFormat, new RTree.Rect(startColumn, startRow, endColumn, endRow), 0L));
                  }

                  result.put(formatProperty.getName(), entries);
               }
            }
         }

         i$ = result.entrySet().iterator();

         while(i$.hasNext()) {
            Entry mapEntry1 = (Entry)i$.next();
            CellFormatEntry.pruneRedundantFormats((Collection)mapEntry1.getValue());
         }
      }

      return result;
   }

   public void addFormatGroup(CellFormatGroup cfg) {
      this.mCellFormats.add(cfg.getRect(), cfg);
   }

   public void addMergeRect(RTree.Rect rect) {
      this.mMergedCells.add(rect, rect);
   }

   public int getLastCellFormatVersion() {
      return this.mCellFormatVersion - 1;
   }

   public int getNextCellFormatVersion() {
      return this.mCellFormatVersion++;
   }

   public void setCellFormatVersion(int cellFormatVersion) {
      this.mCellFormatVersion = cellFormatVersion;
   }

   public void mergeCells(int startRow, int startColumn, int endRow, int endColumn) {
      RTree.Rect mergeRect = new RTree.Rect(startColumn, startRow, endColumn, endRow);
      Collection splitRects = this.doMergeCells(mergeRect);
      this.getWorkbook().postSingleUndoableEdit(new GroupCellsEdit(this, mergeRect, splitRects));
   }

   public Collection<RTree.Rect> doMergeCells(RTree.Rect mergeRect) {
      Collection splitRects = this.mMergedCells.find(mergeRect, new ArrayList());
      Iterator i$ = splitRects.iterator();

      while(i$.hasNext()) {
         RTree.Rect splitRect = (RTree.Rect)i$.next();
         this.doSplitCells(splitRect);
      }

      this.mMergedCells.add(mergeRect, mergeRect);
      this.fireWorksheetFormatChanged(mergeRect.mStartRow, mergeRect.mStartColumn, mergeRect.mEndRow, mergeRect.mEndColumn);
      return splitRects;
   }

   public void splitCells(int startRow, int startColumn, int endRow, int endColumn) {
      RTree.Rect splitRect = new RTree.Rect(startColumn, startRow, endColumn, endRow);
      Collection splitRects = this.mMergedCells.find(splitRect, new ArrayList());
      this.doSplitCells(splitRect);
      this.getWorkbook().postSingleUndoableEdit(new SplitCellsEdit(this, splitRects));
   }

   public void doSplitCells(RTree.Rect splitRect) {
      Collection hits = this.mMergedCells.find(splitRect, new ArrayList());
      Iterator i$ = hits.iterator();

      while(i$.hasNext()) {
         RTree.Rect mergeRect = (RTree.Rect)i$.next();
         this.mMergedCells.remove(mergeRect, mergeRect);
         this.fireWorksheetFormatChanged(mergeRect.mStartRow, mergeRect.mStartColumn, mergeRect.mEndRow, mergeRect.mEndColumn);
      }

   }

   public RTree.Rect queryCellMergeRect(int row, int column) {
      List hits = (List)this.mMergedCells.find(new RTree.Rect(column, row, column, row), new ArrayList());
      return hits.isEmpty()?null:(RTree.Rect)hits.get(0);
   }

   public Properties getProperties() {
      return this.mProperties;
   }

   public void setProperties(Properties properties) {
      this.mProperties = properties;
   }

   public void copyToClipboard(Clipboard clipboard, int startRow, int startColumn, int endRow, int endColumn) {
      CellSelection cellData = new CellSelection(this.getName(), startRow, startColumn, endRow, endColumn, this.queryCellData(startRow, startColumn, endRow, endColumn));
      clipboard.setContents(cellData, cellData);
   }

   private List<CellData> queryCellData(int startRow, int startColumn, int endRow, int endColumn) {
      ArrayList cellData = new ArrayList();
      Iterator rangeIter = this.rangeIterator(startRow, startColumn, endRow, endColumn);

      while(rangeIter.hasNext()) {
         cellData.add(new CellData((Cell)((LinkedListSparse2DArray.CellLink)rangeIter.next()).getData()));
      }

      return cellData;
   }

   public void cutToClipboard(Clipboard clipboard, int startRow, int startColumn, int endRow, int endColumn) {
      try {
         this.getWorkbook().beginGroupEdit();
         this.copyToClipboard(clipboard, startRow, startColumn, endRow, endColumn);
         this.blankCells(startRow, startColumn, endRow, endColumn);
      } finally {
         this.getWorkbook().endGroupEdit();
      }

   }

   protected void blankCells(int startRow, int startColumn, int endRow, int endColumn) {
      try {
         this.getWorkbook().beginGroupEdit();

         for(int row = startRow; row <= endRow; ++row) {
            for(int col = startColumn; col <= endColumn; ++col) {
               this.blankCellValue(row, col);
            }
         }
      } finally {
         this.getWorkbook().endGroupEdit();
      }

   }

   public void pasteFromClipboard(Clipboard clipboard, int startRow, int startColumn, int endRow, int endColumn) {
      try {
         this.getWorkbook().beginGroupEdit();
         DataFlavor[] e = clipboard.getAvailableDataFlavors();
         int len$ = e.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            DataFlavor dataFlavor = e[i$];
            Transferable t;
            if(dataFlavor.equals(CellSelection.getCellSelectionDataFlavor())) {
               t = clipboard.getContents(this);
               CellSelection cellSelection = null;
               if(t != null) {
                  cellSelection = (CellSelection)t.getTransferData(CellSelection.getCellSelectionDataFlavor());
               }

               if(cellSelection != null) {
                  this.paste(cellSelection, startRow, startColumn, endRow, endColumn);
               }
               break;
            }

            if(dataFlavor.equals(DataFlavor.stringFlavor)) {
               t = clipboard.getContents(this);
               this.paste((String)t.getTransferData(DataFlavor.stringFlavor), startRow, startColumn);
               break;
            }
         }
      } catch (Exception var15) {
         throw new RuntimeException(var15);
      } finally {
         this.getWorkbook().endGroupEdit();
      }

   }

   private void paste(String text, int startRow, int startColumn) {
      int row = startRow;
      String[] tokens = text.split("\n");
      String[] arr$ = tokens;
      int len$ = tokens.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String lineToken = arr$[i$];
         if(lineToken != null) {
            int column = startColumn;
            String[] cellTokens = lineToken.split("\t");
            String[] arr$1 = cellTokens;
            int len$1 = cellTokens.length;

            for(int i$1 = 0; i$1 < len$1; ++i$1) {
               String cellToken = arr$1[i$1];
               if(cellToken != null && cellToken.length() > 0) {
                  try {
                     this.setCellValue(cellToken, row, column);
                  } catch (ParseException var17) {
                     this.blankCellValue(row, column);
                  }
               }

               ++column;
            }
         }

         ++row;
      }

   }

   private void paste(CellSelection cellSelection, int startRow, int startColumn, int endRow, int endColumn) {
      if(endRow == startRow && Math.abs(cellSelection.getStartRow() - cellSelection.getEndRow()) > 0) {
         endRow = startRow + Math.abs(cellSelection.getStartRow() - cellSelection.getEndRow());
      }

      if(endColumn == startColumn && Math.abs(cellSelection.getStartColumn() - cellSelection.getEndColumn()) > 0) {
         endColumn = startColumn + Math.abs(cellSelection.getStartColumn() - cellSelection.getEndColumn());
      }

      int targetRow = startRow;

      for(int srcRow = cellSelection.getStartRow(); srcRow <= cellSelection.getEndRow() && targetRow <= endRow; ++targetRow) {
         int targetColumn = startColumn;

         for(int srcColumn = cellSelection.getStartColumn(); srcColumn <= cellSelection.getEndColumn() && targetColumn <= endColumn; ++targetColumn) {
            this.pasteCell(targetRow, targetColumn, cellSelection.getCell(srcRow, srcColumn));
            if(srcColumn == cellSelection.getEndColumn() && targetColumn < endColumn) {
               srcColumn = cellSelection.getStartColumn() - 1;
            }

            ++srcColumn;
         }

         if(srcRow == cellSelection.getEndRow() && targetRow < endRow) {
            srcRow = cellSelection.getStartRow() - 1;
         }

         ++srcRow;
      }

   }

   private void pasteCell(int targetRow, int targetColumn, CellData srcCell) {
      if(srcCell == null) {
         this.blankCellValue(targetRow, targetColumn);
      } else {
         try {
            if(this.getFormulaEngine().isFormula(srcCell.getText())) {
               String e = Formula.rewriteFormula(this, srcCell.getRow(), srcCell.getColumn(), srcCell.getText(), targetRow, targetColumn);
               this.setCellValue(e, targetRow, targetColumn);
            } else {
               this.setCellValue(srcCell.getText(), targetRow, targetColumn);
            }

            if(this.isDesignMode()) {
               Cell e1 = (Cell)this.get(targetRow, targetColumn);
               e1.setInputMapping(srcCell.getInputMapping());
               e1.setOutputMapping(srcCell.getOutputMapping());
            }
         } catch (ParseException var5) {
            this.blankCellValue(targetRow, targetColumn);
         }
      }

   }

   private void blankCellValue(int row, int column) {
      try {
         this.setCellValue("", row, column);
      } catch (ParseException var4) {
         throw new IllegalStateException("Parse exception on blank string!");
      }
   }

   public RTree<CellFormatGroup> getCellFormats() {
      return this.mCellFormats;
   }

   public void registerOriginalCellValue(Cell cell, Object value) {
      this.mOriginalCellValueMap.put(cell.getAddress(), value);
   }

   public Object queryOriginalCellValue(Cell cell) {
      return this.mOriginalCellValueMap.get(cell.getAddress());
   }

   public void clearOriginlCellValues() {
      this.mOriginalCellValueMap.clear();
   }

   public boolean isValid() {
    return isValid;
}

public void setValid(boolean isValid) {
    this.isValid = isValid;
}

static {
      for(int i = 0; i < sColumnHeadings.length; ++i) {
         sColumnMap.put(sColumnHeadings[i], Integer.valueOf(i));
      }

   }
}
