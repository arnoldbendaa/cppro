// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.flatform.model.parser.WorksheetCellRef;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public class LookupFunctionExecutor extends FunctionExecutor {

   public LookupFunctionExecutor() {
      this.setName("LOOKUP");
   }

   public Object execute(List params) {
      if(params != null && params.size() == 3) {
         Object lookupParam = params.get(0);
         if(lookupParam instanceof CellRef) {
            lookupParam = this.getFormulaExecutor().getCellValue((CellRef)lookupParam);
         }

         if(lookupParam instanceof CellErrorValue) {
            return CellErrorValue.NA;
         } else {
            if(lookupParam instanceof String && this.looksLikeANumber(String.valueOf(lookupParam))) {
               String lookupVector = (String)lookupParam;
               if(lookupVector.trim().length() > 0) {
                  try {
                     lookupParam = Double.valueOf(Cell.sInputNumberFormat.parse(lookupVector).doubleValue());
                  } catch (ParseException var9) {
                     ;
                  }
               } else {
                  lookupParam = Cell.sZeroDouble;
               }
            }

            Object lookupVector1 = params.get(1);
            if(!(lookupVector1 instanceof CellRangeRef)) {
               throw new IllegalStateException("Unexpected parameter type for lookup_vector:" + lookupVector1);
            } else {
               CellRangeRef lookupRangeRef = (CellRangeRef)lookupVector1;
               if(!lookupRangeRef.isVector()) {
                  return CellErrorValue.NA;
               } else {
                  Object resultVector = params.get(2);
                  if(!(resultVector instanceof CellRangeRef)) {
                     throw new IllegalStateException("Unexpected parameter type for result_vector:" + lookupVector1);
                  } else {
                     CellRangeRef resultRangeRef = (CellRangeRef)resultVector;
                     if(!resultRangeRef.isVector()) {
                        return CellErrorValue.NA;
                     } else {
                        Object indexValue = this.queryIndexInVector(lookupParam, lookupRangeRef);
                        if(indexValue instanceof CellErrorValue) {
                           return indexValue;
                        } else {
                           int index = ((Number)indexValue).intValue();
                           return this.queryCellValueInVector(index, resultRangeRef);
                        }
                     }
                  }
               }
            }
         }
      } else {
         throw new IllegalStateException("Wrong number of parameters to function LOOKUP(lookup_value,lookup_vector,result_vector)");
      }
   }

   private Object queryIndexInVector(Object value, CellRangeRef vector) {
      int width = vector.getWidth();
      Worksheet refWorksheet = vector.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Iterator cellIter;
      int searchNumeric;
      int searchNumberValue;
      int endRow;
      if(width > 1) {
         searchNumeric = vector.getAbsoluteStartRow(this.getCurrentRow());
         searchNumberValue = vector.getAbsoluteStartColumn(this.getCurrentColumn());
         endRow = vector.getAbsoluteEndColumn(this.getCurrentColumn());
         cellIter = refWorksheet.rangeIterator(searchNumeric, searchNumberValue, searchNumeric, endRow);
      } else {
         searchNumeric = vector.getAbsoluteStartColumn(this.getCurrentColumn());
         searchNumberValue = vector.getAbsoluteStartRow(this.getCurrentRow());
         endRow = vector.getAbsoluteEndRow(this.getCurrentRow());
         cellIter = refWorksheet.rangeIterator(searchNumberValue, searchNumeric, endRow, searchNumeric);
      }

      boolean var19 = value instanceof Number;
      double var20 = var19?((Number)value).doubleValue():0.0D;
      boolean searchString = value instanceof String;
      String searchStringValue = searchString?String.valueOf(value).toUpperCase():null;
      boolean searchComparable = value instanceof Comparable;
      Comparable comparableSearchValue = searchComparable?(Comparable)value:null;
      int index = 0;

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)refWorksheet.get(cellLink.getRow(), cellLink.getColumn());
         if(cell != null) {
            if(var19) {
               Double objectCellValue = (Double)cell.getNumericValue();
               if(objectCellValue == null) {
                  continue;
               }

               if(var20 < objectCellValue.doubleValue()) {
                  if(index == 0) {
                     return CellErrorValue.NA;
                  }

                  return Integer.valueOf(index - 1);
               }

               if(var20 == objectCellValue.doubleValue()) {
                  return Integer.valueOf(index);
               }
            } else if(searchString) {
               String var22 = String.valueOf(cell.getObjectValue()).toUpperCase();
               int comparableCellValue = searchStringValue.compareTo(var22);
               if(comparableCellValue < 0) {
                  if(index == 0) {
                     return CellErrorValue.NA;
                  }

                  return Integer.valueOf(index - 1);
               }

               if(comparableCellValue == 0) {
                  return Integer.valueOf(index);
               }
            } else {
               if(!searchComparable) {
                  return CellErrorValue.NA;
               }

               Object var21 = cell.getObjectValue();
               if(var21 instanceof Comparable) {
                  Comparable var23 = (Comparable)var21;
                  int cmp = comparableSearchValue.compareTo(var23);
                  if(cmp < 0) {
                     if(index == 0) {
                        return CellErrorValue.NA;
                     }

                     return Integer.valueOf(index - 1);
                  }

                  if(cmp == 0) {
                     return Integer.valueOf(index);
                  }
               }
            }
         }

         ++index;
      }

      return Integer.valueOf(index - 1);
   }

   private Object queryCellValueInVector(int index, CellRangeRef cellRangeRef) {
      Worksheet targetWorksheet;
      int targetColumn;
      int startRow;
      int targetRow;
      WorksheetCellRef targetCellRef;
      if(cellRangeRef.getWidth() > 1) {
         targetWorksheet = cellRangeRef.getWorksheet(this.getCurrentWorksheet());
         targetColumn = cellRangeRef.getAbsoluteStartRow(this.getCurrentRow());
         startRow = cellRangeRef.getAbsoluteStartColumn(this.getCurrentColumn());
         targetRow = startRow + index;
         targetCellRef = new WorksheetCellRef(targetWorksheet.getName(), targetRow, true, targetColumn, true);
         return this.getFormulaExecutor().getCellValue(targetCellRef);
      } else {
         targetWorksheet = cellRangeRef.getWorksheet(this.getCurrentWorksheet());
         targetColumn = cellRangeRef.getAbsoluteStartColumn(this.getCurrentColumn());
         startRow = cellRangeRef.getAbsoluteStartRow(this.getCurrentRow());
         targetRow = startRow + index;
         targetCellRef = new WorksheetCellRef(targetWorksheet.getName(), targetColumn, true, targetRow, true);
         return this.getFormulaExecutor().getCellValue(targetCellRef);
      }
   }

   private boolean looksLikeANumber(String s) {
      int length = s.length();
      if(length == 0) {
         return false;
      } else {
         for(int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            if(",.+-E".indexOf(c) < 0 && !Character.isDigit(c)) {
               return false;
            }
         }

         return true;
      }
   }
}
