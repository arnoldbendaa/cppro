// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRange;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class HlookupFunctionExecutor extends FunctionExecutor {

   private byte mLookupType;
   private static final byte NUMBER = 0;
   private static final byte STRING = 1;
   private static final byte REF = 2;


   public HlookupFunctionExecutor() {
      this.setName("HLOOKUP");
   }

   public Object execute(List params) {
      if(params != null && (params.size() == 3 || params.size() == 4)) {
         Object param1 = params.get(0);
         String lookupValue = null;
         if(param1 instanceof CellRef) {
            lookupValue = this.getLookupValue((CellRef)param1);
            if(lookupValue == null) {
               return CellErrorValue.NAME;
            }

            this.mLookupType = 2;
         } else if(param1 instanceof Number) {
            lookupValue = String.valueOf(param1);
            this.mLookupType = 0;
         } else if(param1 instanceof String) {
            lookupValue = (String)param1;
            if("".equals(lookupValue)) {
               return CellErrorValue.NA;
            }

            this.mLookupType = 1;
         } else {
            if(!(param1 instanceof Boolean)) {
               return CellErrorValue.VALUE;
            }

            lookupValue = ((Boolean)param1).toString();
            this.mLookupType = 1;
         }

         Object param2 = params.get(1);
         CellRange tableArray = null;
         if(param2 instanceof CellRange) {
            tableArray = (CellRange)param2;
            Object param3 = params.get(2);
            boolean rowIndex = true;
            int rowIndex1;
            if(param3 instanceof CellRef) {
               rowIndex1 = this.getRowIndex((CellRef)param3);
               if(rowIndex1 == -1) {
                  return CellErrorValue.VALUE;
               }
            } else if(param3 instanceof Number) {
               rowIndex1 = ((Number)param3).intValue();
            } else {
               if(!(param3 instanceof String)) {
                  return CellErrorValue.VALUE;
               }

               try {
                  rowIndex1 = Integer.parseInt((String)param3);
               } catch (NumberFormatException var10) {
                  return CellErrorValue.REF;
               }
            }

            boolean rangeLookup = true;
            if(params.size() == 4) {
               Object param4 = params.get(3);
               if(param4 instanceof Boolean) {
                  rangeLookup = ((Boolean)param4).booleanValue();
               } else {
                  if(!(param4 instanceof CellRef)) {
                     return CellErrorValue.VALUE;
                  }

                  param4 = this.getRangeLookup((CellRef)param4);
                  if(param4 == null) {
                     return CellErrorValue.VALUE;
                  }

                  rangeLookup = ((Boolean)param4).booleanValue();
               }
            }

            return this.hlookup(lookupValue, tableArray, rowIndex1, rangeLookup);
         } else {
            return CellErrorValue.REF;
         }
      } else {
         throw new IllegalStateException("Wrong number of parameters to function HLOOKUP()");
      }
   }

   private Object hlookup(String lookupValue, CellRange cellRange, int rowIndex, boolean rangeLookup) {
      String result = null;
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      if(rowIndex < 1) {
         return CellErrorValue.VALUE;
      } else if(rowIndex > endRow - startRow + 1) {
         return CellErrorValue.REF;
      } else {
         int selectedColIdx = -1;
         String cellValue = null;
         boolean cLookup = true;
         double dLookup = -1.0D;
         double dCellValue = -1.0D;
         boolean isFirst = true;
         double variance = -1.0D;
         boolean isNumber = this.isNumber(lookupValue);
         Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
         Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, startRow, endColumn);

         while(cellIter.hasNext()) {
            LinkedListSparse2DArray.CellLink cell = (LinkedListSparse2DArray.CellLink)cellIter.next();
            Cell cell1 = (Cell)refWorksheet.get(cell.getRow(), cell.getColumn());
            if(cell1 != null) {
               cellValue = cell1.getStringValue();
               if(cellValue != null && !cellValue.equals("") && (!this.isNumber(cellValue) || this.mLookupType != 1) && (this.isNumber(cellValue) || this.mLookupType != 0)) {
                  if(!rangeLookup) {
                     if(lookupValue.toUpperCase().equals(cellValue.toUpperCase())) {
                        selectedColIdx = cell.getColumn();
                        break;
                     }

                     if(Pattern.matches("[0-9]*.0", lookupValue) && lookupValue.substring(0, lookupValue.indexOf(".")).equals(cellValue)) {
                        selectedColIdx = cell.getColumn();
                        break;
                     }
                  } else if(this.isNumber(cellValue) && isNumber) {
                     dCellValue = Double.parseDouble(cellValue);
                     dLookup = Double.parseDouble(lookupValue);
                     if(dCellValue == dLookup) {
                        selectedColIdx = cell.getColumn();
                        break;
                     }

                     if(isFirst) {
                        variance = dLookup - dCellValue;
                        isFirst = false;
                     }

                     if(dLookup - dCellValue >= 0.0D && dLookup - dCellValue <= variance) {
                        variance = dLookup - dCellValue;
                        selectedColIdx = cell.getColumn();
                     }
                  } else if(!this.isNumber(cellValue) && !isNumber) {
                     if(cellValue.toUpperCase().equals(lookupValue.toUpperCase())) {
                        selectedColIdx = cell.getColumn();
                        break;
                     }

                     char cCellValue = cellValue.charAt(0);
                     char cLookup1 = lookupValue.charAt(0);
                     if(isFirst) {
                        variance = (double)(cLookup1 - cCellValue);
                        isFirst = false;
                     }

                     if(cLookup1 - cCellValue >= 0 && (double)(cLookup1 - cCellValue) <= variance) {
                        variance = (double)(cLookup1 - cCellValue);
                        selectedColIdx = cell.getColumn();
                     }
                  }
               }
            }
         }

         if(selectedColIdx == -1) {
            return CellErrorValue.NA;
         } else {
            Cell cell2 = (Cell)refWorksheet.get(rowIndex + startRow - 1, selectedColIdx);
            result = cell2 != null?cell2.getStringValue():"";
            return result;
         }
      }
   }

   private boolean isNumber(String value) {
      return Pattern.matches("[0-9]+", value) || Pattern.matches("[0-9]*.[0-9]+", value);
   }

   private Object getRangeLookup(CellRef cellRef) {
      Cell cell = cellRef.getCell(this.getFormulaExecutor().getWorksheet(), this.getFormulaExecutor().getCurrentRow(), this.getFormulaExecutor().getCurrentColumn());
      Boolean result = null;
      if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
         if(cell.getStringValue().toUpperCase().equals("TRUE")) {
            result = Boolean.TRUE;
         } else {
            if(!cell.getStringValue().toUpperCase().equals("FALSE")) {
               return null;
            }

            result = Boolean.FALSE;
         }
      }

      return result;
   }

   private String getLookupValue(CellRef cellRef) {
      Cell cell = cellRef.getCell(this.getFormulaExecutor().getWorksheet(), this.getFormulaExecutor().getCurrentRow(), this.getFormulaExecutor().getCurrentColumn());
      String result = null;
      if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
         result = cell.getStringValue();
      }

      return result;
   }

   private int getRowIndex(CellRef cellRef) {
      Cell cell = cellRef.getCell(this.getFormulaExecutor().getWorksheet(), this.getFormulaExecutor().getCurrentRow(), this.getFormulaExecutor().getCurrentColumn());
      int result = -1;
      if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
         result = Integer.parseInt(cell.getStringValue());
      }

      return result;
   }
}
