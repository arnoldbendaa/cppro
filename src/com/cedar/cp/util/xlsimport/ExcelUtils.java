// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xlsimport;

import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import java.util.Date;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;

public class ExcelUtils {

   public static boolean doesCellContain(Sheet sheet, int row, short col, String value) {
      if(sheet == null) {
         return false;
      } else {
         Cell[] rows = row >= 0 && row < sheet.getRows()?sheet.getRow(row):null;
         if(rows == null) {
            return false;
         } else {
            Cell cell = col >= 0 && col < rows.length?rows[col]:null;
            if(cell == null) {
               return false;
            } else {
               String testString = cell.getContents();
               return testString != null && testString.equals(value);
            }
         }
      }
   }

   public static String getMandatoryStringCell(Sheet sheet, int row, short col) throws ExcelGenericImportException {
      if(sheet == null) {
         throw new ExcelGenericImportException("Can\'t get mandatory cell for a null sheet");
      } else {
         Cell[] rows = row >= 0 && row < sheet.getRows()?sheet.getRow(row):null;
         if(rows == null) {
            throw new ExcelGenericImportException("Can\'t get mandatory row " + row);
         } else {
            Cell cell = col >= 0 && col < rows.length?rows[col]:null;
            if(cell == null) {
               return "";
            } else {
               String value = cell.getContents();
               if(value == null) {
                  throw new ExcelGenericImportException("Can\'t get mandatory String value for cell sheet=" + sheet.getName() + ",row=" + row + ",col=" + col);
               } else {
                  return value;
               }
            }
         }
      }
   }

   public static String getOptionalStringCell(Sheet sheet, int row, short col) throws ExcelGenericImportException {
      if(sheet == null) {
         return "";
      } else {
         Cell[] rows = row >= 0 && row < sheet.getRows()?sheet.getRow(row):null;
         if(rows == null) {
            return "";
         } else {
            Cell cell = col >= 0 && col < rows.length?rows[col]:null;
            if(cell == null) {
               return "";
            } else {
               String value = getCellStringValue(cell);
               return value == null?"":value;
            }
         }
      }
   }

   public static String getMandatoryIntegerCell(Sheet sheet, int row, short col) throws ExcelGenericImportException {
      if(sheet == null) {
         throw new ExcelGenericImportException("Can\'t get mandatory cell for a null sheet");
      } else {
         Cell[] rows = row >= 0 && row < sheet.getRows()?sheet.getRow(row):null;
         if(rows == null) {
            throw new ExcelGenericImportException("Can\'t get mandatory row sheet=" + sheet.getName() + ",row=" + row);
         } else {
            Cell cell = col >= 0 && col < rows.length?rows[col]:null;
            if(cell == null) {
               throw new ExcelGenericImportException("Can\'t get mandatory cell (due to invalid column) sheet=" + sheet.getName() + ",row=" + row + ",col=" + col);
            } else {
               String value = getCellStringValue(cell);
               if(value == null) {
                  throw new ExcelGenericImportException("Can\'t get mandatory Integer value for cell sheet=" + sheet.getName() + ",row=" + row + ",col=" + col);
               } else {
                  Double numberValue = Double.valueOf(value);
                  return "" + numberValue.intValue();
               }
            }
         }
      }
   }

   public static int getMandatoryIntegerCellAsInt(Sheet sheet, int row, short col) throws ExcelGenericImportException {
      if(sheet == null) {
         throw new ExcelGenericImportException("Can\'t get mandatory cell for a null sheet");
      } else {
         Cell[] rows = row >= 0 && row < sheet.getRows()?sheet.getRow(row):null;
         if(rows == null) {
            throw new ExcelGenericImportException("Can\'t get mandatory sheet=" + sheet.getName() + ",row " + row);
         } else {
            Cell cell = col >= 0 && col < rows.length?rows[col]:null;
            if(cell == null) {
               throw new ExcelGenericImportException("Can\'t get mandatory cell (due to invalid column) sheet=" + sheet.getName() + ",row=" + row + ",col=" + col);
            } else {
               Double numberValue = Double.valueOf(cell.getContents());
               return numberValue.intValue();
            }
         }
      }
   }

   public static Date getMandatoryDateCell(Sheet sheet, int row, short col) throws ExcelGenericImportException {
      if(sheet == null) {
         throw new ExcelGenericImportException("Can\'t get mandatory cell for a null sheet");
      } else {
         Cell[] rows = row >= 0 && row < sheet.getRows()?sheet.getRow(row):null;
         if(rows == null) {
            throw new ExcelGenericImportException("Can\'t get mandatory row sheet=" + sheet.getName() + ",row=" + row);
         } else {
            Cell cell = col >= 0 && col < rows.length?rows[col]:null;
            if(cell == null) {
               throw new ExcelGenericImportException("Can\'t get mandatory cell (due to invalid column) sheet=" + sheet.getName() + ",row=" + row + ",col=" + col);
            } else {
               Date value = getCellDateValue(cell);
               if(value == null) {
                  throw new ExcelGenericImportException("Can\'t get mandatory Date value for cell sheet=" + sheet.getName() + ",row=" + row + ",col=" + col);
               } else {
                  return value;
               }
            }
         }
      }
   }

   public static String getCellStringValue(Cell cell) {
      return cell.getContents();
   }

   public static Date getCellDateValue(Cell cell) {
      Date value = null;
      if(cell.getType() == CellType.DATE) {
         value = ((DateCell)cell).getDate();
      }

      return value;
   }
}
