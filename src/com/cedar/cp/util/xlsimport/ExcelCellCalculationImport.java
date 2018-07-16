// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xlsimport;

import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import com.cedar.cp.util.xlsimport.ExcelUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelCellCalculationImport {

   private PrintStream mOutputStream;
   private Workbook mWorkbook;
   private String mCurrentSheetName;
   private static final String IMPORT_DEFINITION = "ImportDefinition";
   private static final String MODEL_VIS_ID = "modelVisId";
   private static final String FINANCE_CUBE_VIS_ID = "financeCubeVisId";
   private static final String BUDGET_CYCLE_VIS_ID = "budgetCycleVisId";
   private static final String HEADER_SIGN = "#Header";
   private static final String ROW_SIGN = "#Row";
   private static final String UPDATE_TYPE_SIGN = "updateType";
   private static final String ADDRESS_SIGN = "address";
   private static final String CALC_TYPE = "type";
   private static final String CALC_TYPE_DYNAMIC = "dynamic";
   private static final String CALC_TYPE_FIXED = "fixed";
   private static final String INDEX = "index";
   private static final String LINE_FEED = "\r\n";
   private static final char INDEX_CHARACTER_INDICATOR = '$';
   private Sheet mMainSheet;
   private String mSign;
   private String mType;
   private int mRow;
   private int mColumn;
   private String mId;
   private String mValue;


   public static void main(String[] args) {
      FileInputStream is = null;
      PrintStream ps = null;

      try {
         String ex = "C:\\projects\\data\\ngh\\Failed_1CC.xls";
         is = new FileInputStream(ex);
         String outputFileName = "c:\\projects\\data\\ngh\\Failed_1CC.xml";
         File outputFile = new File(outputFileName);
         ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
         ExcelCellCalculationImport processor = new ExcelCellCalculationImport();
         processor.processStream(is, ps);
      } catch (Exception var19) {
         var19.printStackTrace();
      } finally {
         if(is != null) {
            try {
               is.close();
            } catch (Exception var18) {
               ;
            }
         }

         if(ps != null) {
            try {
               ps.close();
            } catch (Exception var17) {
               ;
            }
         }

      }

   }

   public void processStream(InputStream is, PrintStream os) throws ExcelGenericImportException, IOException {
      if(is == null) {
         throw new IllegalArgumentException("Input stream cannot be null");
      } else if(os == null) {
         throw new IllegalArgumentException("Output stream cannot be null");
      } else {
         this.mOutputStream = os;

         try {
            this.mWorkbook = Workbook.getWorkbook(is);
         } catch (BiffException var4) {
            throw new ExcelGenericImportException(var4.getMessage());
         }

         this.findAndProcessMainSheet(this.mWorkbook);
      }
   }

   private void findAndProcessMainSheet(Workbook wb) throws ExcelGenericImportException {
      this.mMainSheet = this.getMainSheet(wb);
      if(this.mMainSheet == null) {
         throw new ExcelGenericImportException("Can\'t find \'ImportDefinition\' sheet.");
      } else {
         this.mOutputStream.println("<?xml version=\"1.0\"?>");
         this.mOutputStream.println("<cell-calc-import xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
         this.mOutputStream.println("\t\t\t\txsi:noNamespaceSchemaLocation=\"cell-calc-import.xsd\"");
         if(!ExcelUtils.doesCellContain(this.mMainSheet, 1, (short)0, "modelVisId")) {
            throw this.newExcelGenericImportException("Can\'t find Model Id tag");
         } else if(!ExcelUtils.doesCellContain(this.mMainSheet, 2, (short)0, "financeCubeVisId")) {
            throw this.newExcelGenericImportException("Can\'t find Finance Cube Id tag");
         } else if(!ExcelUtils.doesCellContain(this.mMainSheet, 3, (short)0, "budgetCycleVisId")) {
            throw this.newExcelGenericImportException("Can\'t find Budget Cycle Id tag");
         } else {
            String modelId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, 1, (short)1);
            String financeCubeId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, 2, (short)1);
            String budgetCylceId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, 3, (short)1);
            this.mOutputStream.println("\t\t\t\tmodelVisId=\"" + modelId + "\"");
            this.mOutputStream.println("\t\t\t\tfinanceCubeVisId=\"" + financeCubeId + "\"");
            this.mOutputStream.println("\t\t\t\tbudgetCycleVisId=\"" + budgetCylceId + "\">");
            this.processMainSheet(this.mMainSheet);
            this.mOutputStream.println("</cell-calc-import>");
         }
      }
   }

   private Sheet getMainSheet(Workbook wb) throws ExcelGenericImportException {
      this.mCurrentSheetName = "ImportDefinition";
      Sheet sheet = wb.getSheet(this.mCurrentSheetName);
      if(sheet == null) {
         sheet = wb.getSheet(0);
      }

      return sheet;
   }

   private ExcelGenericImportException newExcelGenericImportException(String reason) {
      return new ExcelGenericImportException(this.mCurrentSheetName, reason);
   }

   private void processMainSheet(Sheet mainSheet) throws ExcelGenericImportException {
      this.mRow = 4;
      boolean outputDataTag = false;

      while(this.mRow < mainSheet.getRows()) {
         this.mSign = ExcelUtils.getMandatoryStringCell(mainSheet, this.mRow, (short)0);
         if(!this.isEmpty(this.mSign)) {
            if(this.mSign.equals("#Header")) {
               this.processHeader();
            } else if(this.mSign.equals("#Row")) {
               this.mOutputStream.println("\t\t<data>");
               outputDataTag = true;
               ++this.mRow;
            } else {
               this.processCalcRows();
            }
         } else {
            ++this.mRow;
         }
      }

      if(!this.isEmpty(this.mType) && outputDataTag) {
         this.mOutputStream.println("\t\t</data>");
      }

      this.mOutputStream.println("\t</cell-calc>");
   }

   private void processHeader() throws ExcelGenericImportException {
      String mValue = "";
      if(this.mRow > 4) {
         if(!this.isEmpty(this.mType)) {
            this.mOutputStream.println("\t\t</data>");
         }

         this.mOutputStream.println("\t</cell-calc>");
      }

      ++this.mRow;
      this.mSign = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)0);
      if(!this.isEmpty(this.mSign) && this.mSign.equals("updateType")) {
         if(this.mSign.equals("updateType")) {
            mValue = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)1);
         }

         this.mOutputStream.println("\t<cell-calc updateType=\"" + mValue + "\">");
         ++this.mRow;
         mValue = "";
         this.mOutputStream.println("\t\t<address>");
         this.mSign = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)0);
         if(!this.isEmpty(this.mSign) && this.mSign.equals("address")) {
            if(this.mSign.equals("address")) {
               Cell[] row = this.trimToPopulatedCells(this.mMainSheet.getRow(this.mRow));
               int lastPopulatedCellIndex = this.queryLastPopulatedCellIndex(row);

               for(int i = 1; i <= lastPopulatedCellIndex; ++i) {
                  if(this.isEmpty(row[i].getContents())) {
                     throw this.newExcelGenericImportException("Expected dimension for row " + this.mRow);
                  }

                  if(i == lastPopulatedCellIndex) {
                     this.mOutputStream.println("\t\t\t<dataType>" + row[i].getContents() + "</dataType>");
                  } else {
                     this.mOutputStream.println("\t\t\t<dim" + (i - 1) + ">" + row[i].getContents() + "</dim" + (i - 1) + ">");
                  }
               }
            }

            this.mOutputStream.println("\t\t</address>");
            ++this.mRow;
            this.mType = "";
            this.mSign = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)0);
            if(this.mSign.equals("type")) {
               this.mType = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)1);
               ++this.mRow;
            }

         } else {
            throw this.newExcelGenericImportException("Unexpected row keyword in column A for row " + this.mRow);
         }
      } else {
         throw new ExcelGenericImportException("Unexpected row keyword in column A for row " + this.mRow);
      }
   }

   private int queryLastPopulatedCellIndex(Cell[] row) {
      int i;
      for(i = row.length - 1; i > 0; --i) {
         Cell cell = row[i];
         if(cell.getType() != CellType.EMPTY) {
            break;
         }
      }

      return i;
   }

   private Cell[] trimToPopulatedCells(Cell[] row) {
      int lastPopulatedCellIndex = this.queryLastPopulatedCellIndex(row);
      return lastPopulatedCellIndex >= 0?(Cell[])Arrays.copyOf(row, lastPopulatedCellIndex + 1):new Cell[0];
   }

   private void processCalcRows() throws ExcelGenericImportException {
      Cell[] row = this.trimToPopulatedCells(this.mMainSheet.getRow(this.mRow));
      this.mId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)0);
      this.mValue = ExcelUtils.getOptionalStringCell(this.mMainSheet, this.mRow + 1, (short)0);
      if(!"updateType".equals(this.mId)) {
         throw this.newExcelGenericImportException("Unexpected row keyword in column A for row " + this.mRow);
      } else {
         if(this.isEmpty(this.mValue)) {
            this.mOutputStream.println("\t\t\t<row>");
         } else {
            this.mOutputStream.println("\t\t\t<row " + this.mId + "=\"" + this.mValue + "\">");
         }

         StringBuffer updatedData = new StringBuffer();
         boolean isWhere = false;

         for(int col = 1; col < row.length; ++col) {
            if(this.mType.equals("fixed")) {
               this.mId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)col);
               this.mValue = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow + 1, (short)col);
               if(col == 1) {
                  if(!"index".equalsIgnoreCase(this.mId) && this.isDollarPreFixed(this.mId) && !this.getId(this.mId).equalsIgnoreCase("index")) {
                     throw this.newExcelGenericImportException("Expected index in column B for row " + this.mRow);
                  }

                  this.mOutputStream.println("\t\t\t\t<address " + this.getId(this.mId) + "=\"" + this.mValue + "\" />");
               } else if(this.isEmpty(this.mId)) {
                  if(col != row.length - 1) {
                     throw this.newExcelGenericImportException("Expected field for row " + this.mRow);
                  }
               } else {
                  if(this.isDollarPreFixed(this.mId)) {
                     throw this.newExcelGenericImportException("Expected updated field for row " + this.mRow);
                  }

                  this.mOutputStream.println("\t\t\t\t<column>");
                  this.mOutputStream.println("\t\t\t\t\t<id>" + this.getId(this.mId) + "</id>");
                  this.mOutputStream.println("\t\t\t\t\t<value>" + this.mValue + "</value>");
                  this.mOutputStream.println("\t\t\t\t</column>");
               }
            } else {
               if(!this.mType.equals("dynamic")) {
                  throw this.newExcelGenericImportException("Expected type of calculator");
               }

               this.mId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow, (short)col);
               this.mValue = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mRow + 1, (short)col);
               if(!this.isEmpty(this.mId)) {
                  if(!this.isDollarPreFixed(this.mId)) {
                     updatedData.append("\t\t\t\t<column>\r\n");
                     updatedData.append("\t\t\t\t\t<id>" + this.getId(this.mId) + "</id>" + "\r\n");
                     updatedData.append("\t\t\t\t\t<value>" + this.mValue + "</value>" + "\r\n");
                     updatedData.append("\t\t\t\t</column>\r\n");
                  } else {
                     if(!isWhere) {
                        this.mOutputStream.println("\t\t\t\t<address>");
                        isWhere = true;
                     }

                     this.mOutputStream.println("\t\t\t\t\t<column>");
                     this.mOutputStream.println("\t\t\t\t\t\t<id>" + this.getId(this.mId) + "</id>");
                     this.mOutputStream.println("\t\t\t\t\t\t<value>" + this.mValue + "</value>");
                     this.mOutputStream.println("\t\t\t\t\t</column>");
                  }
               }

               if(col == row.length - 1 && isWhere) {
                  this.mOutputStream.println("\t\t\t\t</address>");
               }
            }
         }

         if(updatedData.length() != 0) {
            this.mOutputStream.print(updatedData.toString());
         }

         this.mOutputStream.println("\t\t\t</row>");
         ++this.mRow;
         ++this.mRow;
      }
   }

   private boolean isEmpty(String mValue) {
      return mValue == null || mValue.equals("");
   }

   private boolean isDollarPreFixed(String mValue) {
      return !this.isEmpty(mValue) && mValue.charAt(0) == 36;
   }

   private String getId(String mValue) {
      return mValue != null && !mValue.equals("")?(mValue.charAt(0) == 36?mValue.substring(1):mValue):"";
   }
}
