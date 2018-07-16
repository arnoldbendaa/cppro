// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
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

public class MaxFunctionExecutor extends FunctionExecutor {

   private boolean mFirstValue;


   public MaxFunctionExecutor() {
      this.setName("MAX");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function MAX()");
      } else if(params.size() > 30) {
         throw new IllegalStateException("Too many arguments to function MAX(). Maximum of 30 arguments");
      } else {
         double maxValue = 0.0D;
         this.mFirstValue = true;
         Iterator i$ = params.iterator();

         while(i$.hasNext()) {
            Object param = i$.next();
            if(param instanceof CellRef) {
               if(((CellRef)param).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               CellRef e = (CellRef)param;
               Worksheet refWorksheet = e.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cell = e.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
               if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
                  Object valueObject = this.mFormulaExecutor.getNumericCellValue(e);
                  if(valueObject instanceof Number) {
                     if(this.mFirstValue) {
                        maxValue = ((Number)valueObject).doubleValue();
                        this.mFirstValue = false;
                     } else {
                        maxValue = Math.max(((Number)valueObject).doubleValue(), maxValue);
                     }
                  }
               }
            } else if(param instanceof CellRange) {
               if(((CellRange)param).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               maxValue = this.max((CellRange)param, maxValue);
            } else if(param instanceof Number) {
               if(this.mFirstValue) {
                  maxValue = ((Number)param).doubleValue();
                  this.mFirstValue = false;
               } else {
                  maxValue = Math.max(((Number)param).doubleValue(), maxValue);
               }
            } else if(!(param instanceof CellErrorValue)) {
               if(param instanceof String) {
                  try {
                     double e1 = Double.parseDouble((String)param);
                     if(this.mFirstValue) {
                        maxValue = e1;
                        this.mFirstValue = false;
                     } else {
                        maxValue = Math.max(e1, maxValue);
                     }
                  } catch (NumberFormatException var10) {
                     return CellErrorValue.VALUE;
                  }
               } else {
                  if(!(param instanceof Boolean)) {
                     throw new IllegalArgumentException("Unexpected parameter " + param + " in MAX() function");
                  }

                  if(this.mFirstValue) {
                     maxValue = (double)(((Boolean)param).booleanValue() == Boolean.TRUE.booleanValue()?1:0);
                     this.mFirstValue = false;
                  } else {
                     maxValue = Math.max(((Boolean)param).booleanValue() == Boolean.TRUE.booleanValue()?1.0D:0.0D, maxValue);
                  }
               }
            }
         }

         return Double.valueOf(maxValue);
      }
   }

   private double max(CellRange cellRange, double max) {
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      double result = max;
      double value = 0.0D;
      Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, endRow, endColumn);

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)refWorksheet.get(cellLink.getRow(), cellLink.getColumn());
         if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
            Object numberValue = cell.getNumericValue();
            if(numberValue instanceof Number) {
               value = ((Number)numberValue).doubleValue();
               if(this.mFirstValue) {
                  result = value;
                  this.mFirstValue = false;
               } else if(value > result) {
                  result = value;
               }
            }
         }
      }

      return result;
   }
}
