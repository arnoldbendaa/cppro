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
import com.cedar.cp.util.flatform.model.parser.ValueUtils;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.util.Iterator;
import java.util.List;

public class NpvFunctionExecutor extends FunctionExecutor {

   private double mRate = 0.0D;
   private int mIndex = 0;


   public NpvFunctionExecutor() {
      this.setName("NPV");
   }

   public Object execute(List params) {
      double value = 0.0D;
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function NPV()");
      } else if(params.size() == 1) {
         throw new IllegalStateException("Wrong number of parameters to function NPV()");
      } else if(params.size() > 30) {
         throw new IllegalStateException("Too many arguments to function NPV(). Maximum of 30 arguments");
      } else {
         Object param0 = params.get(0);
         if(param0 instanceof CellErrorValue) {
            return param0;
         } else {
            if(param0 instanceof CellRef) {
               if(((CellRef)param0).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               CellRef i$ = (CellRef)param0;
               if(Math.abs(i$.getColumn()) > this.mFormulaExecutor.getCurrentColumn()) {
                  return CellErrorValue.NAME;
               }

               Worksheet param = i$.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cell = i$.getCell(param, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
               if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
                  param0 = ValueUtils.getPercentValue(cell.getStringValue());
                  if(param0 == null) {
                     return CellErrorValue.VALUE;
                  }

                  this.mRate = ((Number)param0).doubleValue();
               }
            } else if(param0 instanceof Number) {
               this.mRate = ((Number)param0).doubleValue();
            } else {
               if(!(param0 instanceof String)) {
                  return CellErrorValue.VALUE;
               }

               param0 = ValueUtils.getPercentValue((String)param0);
               if(param0 == null) {
                  return CellErrorValue.VALUE;
               }

               this.mRate = ((Number)param0).doubleValue();
            }

            Iterator i$1 = params.iterator();

            while(i$1.hasNext()) {
               Object param1 = i$1.next();
               if(this.mIndex == 0) {
                  this.mIndex = 1;
               } else if(param1 instanceof CellRef) {
                  if(((CellRef)param1).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                     return CellErrorValue.REF;
                  }

                  value += this.npv((CellRef)param1);
               } else if(param1 instanceof CellRange) {
                  if(((CellRange)param1).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                     return CellErrorValue.REF;
                  }

                  value += this.npv((CellRange)param1);
               } else if(param1 instanceof Number) {
                  value += this.npv(((Number)param1).doubleValue());
               } else if(!(param1 instanceof CellErrorValue)) {
                  if(!(param1 instanceof String)) {
                     throw new IllegalArgumentException("Unexpected parameter " + param1 + " in NPV() function");
                  }

                  param1 = ValueUtils.getPercentValue((String)param1);
                  if(param1 == null) {
                     return CellErrorValue.VALUE;
                  }

                  value += this.npv(((Number)param1).doubleValue());
               }
            }

            return Double.valueOf(value);
         }
      }
   }

   private double npv(double value) {
      double result = 0.0D;
      result = value / Math.pow(1.0D + this.mRate, (double)this.mIndex);
      ++this.mIndex;
      return result;
   }

   private double npv(CellRef cellRef) {
      Object valueObject = this.mFormulaExecutor.getNumericCellValue(cellRef);
      double value = valueObject instanceof Number?((Number)valueObject).doubleValue():0.0D;
      return this.npv(value);
   }

   private double npv(CellRange cellRange) {
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      double result = 0.0D;
      Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, endRow, endColumn);

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)refWorksheet.get(cellLink.getRow(), cellLink.getColumn());
         if(cell != null) {
            Object numberValue = cell.getNumericValue();
            if(numberValue instanceof Number) {
               result += this.npv(((Number)numberValue).doubleValue());
            }
         }
      }

      return result;
   }
}
