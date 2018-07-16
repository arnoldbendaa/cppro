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
import com.cedar.cp.util.flatform.model.parser.ExpressionEvaluator;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.util.Iterator;
import java.util.List;

public class SumifFunctionExecutor extends FunctionExecutor {

   ExpressionEvaluator mEvaluator = new ExpressionEvaluator();


   public SumifFunctionExecutor() {
      this.setName("SUMIF");
   }

   public Object execute(List params) {
      double value = 0.0D;
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function SUMIF(). Expected 2 (or 3) got 0");
      } else if(params.size() != 2 && params.size() != 3) {
         throw new IllegalStateException("Wrong number of parameters to function SUMIF(). Expected 2 (or 3) got " + params.size());
      } else {
         Object param1 = params.get(0);
         if(!(param1 instanceof CellRef) && !(param1 instanceof CellRange)) {
            return CellErrorValue.NAME;
         } else {
            Object param2 = params.get(1);
            Worksheet param3;
            String param21;
            if(param2 instanceof CellRef) {
               param3 = ((CellRef)param2).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cell = ((CellRef)param2).getCell(param3, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
               param21 = cell.getStringValue();
            } else if(param2 instanceof String) {
               param21 = param2.toString();
            } else {
               if(!(param2 instanceof Number)) {
                  if(param2 instanceof CellRange) {
                     return Integer.valueOf(0);
                  }

                  return CellErrorValue.VALUE;
               }

               param21 = param2.toString();
            }

            param3 = null;
            if(params.size() == 3) {
               Object param31 = params.get(2);
               return !(param31 instanceof CellRef) && !(param31 instanceof CellRange)?CellErrorValue.NAME:Double.valueOf(this.sumif(param1, param21, param31));
            } else {
               return Double.valueOf(this.sumif(param1, param21));
            }
         }
      }
   }

   private double sumif(Object range, Object criteria) {
      double result = 0.0D;
      if(range instanceof CellRef) {
         result = this.sumif((CellRef)range, criteria);
      } else if(range instanceof CellRange) {
         result = this.sumif((CellRange)range, criteria);
      }

      return result;
   }

   private double sumif(CellRef cellRef, Object objCriteria) {
      double result = 0.0D;
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      result = this.sumif(cell, objCriteria);
      return result;
   }

   private double sumif(CellRange cellRange, Object objCriteria) {
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      double result = 0.0D;
      Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());

      Cell cell;
      for(Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, endRow, endColumn); cellIter.hasNext(); result += this.sumif(cell, objCriteria)) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         cell = (Cell)refWorksheet.get(cellLink.getRow(), cellLink.getColumn());
      }

      return result;
   }

   private double sumif(Cell cell, Object objCriteria) {
      double result = 0.0D;
      String criteria = String.valueOf(objCriteria);
      if(!this.isEmptyCell(cell)) {
         String value = cell.getStringValue();
         if(!this.mEvaluator.isNumber(value) && !this.mEvaluator.isDateTime(value)) {
            return result;
         }

         if(!this.mEvaluator.isEqualExp(criteria)) {
            if(criteria.equals("<>*")) {
               if(this.mEvaluator.isDateTime(value)) {
                  result += this.mEvaluator.convertDate2Number(value);
               } else if(this.mEvaluator.isNumber(value)) {
                  result += Double.parseDouble(value);
               }
            } else if(this.mEvaluator.isExp(criteria)) {
               if(this.mEvaluator.evaluate(criteria, value)) {
                  result += Double.parseDouble(value);
               }
            } else if(this.mEvaluator.isNumber(value) && this.mEvaluator.evaluateWildCard(criteria, value)) {
               result += Double.parseDouble(value);
            }
         }
      }

      return result;
   }

   private double sumif(Object range, Object criteria, Object sum_range) {
      double result = 0.0D;
      int var20;
      if(range instanceof CellRef) {
         if(this.countif((CellRef)range, criteria) == 1) {
            if(sum_range instanceof CellRef) {
               CellRef sColSum = (CellRef)sum_range;
               Worksheet sRowSum = sColSum.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cellRange = sColSum.getCell(sRowSum, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
               result = this.cal(cellRange);
            } else if(sum_range instanceof CellRange) {
               CellRange var19 = (CellRange)sum_range;
               var20 = Math.min(var19.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), var19.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
               int var22 = Math.min(var19.getStartRef().getAbsoluteRow(this.getCurrentRow()), var19.getEndRef().getAbsoluteRow(this.getCurrentRow()));
               Worksheet startColumn = var19.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell endColumn = (Cell)startColumn.get(var22, var20);
               result = this.cal(endColumn);
            }
         }
      } else if(range instanceof CellRange) {
         int var18 = 0;
         var20 = 0;
         CellRange var21 = (CellRange)range;
         int var23 = Math.min(var21.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), var21.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
         int var24 = Math.max(var21.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), var21.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
         int startRow = Math.min(var21.getStartRef().getAbsoluteRow(this.getCurrentRow()), var21.getEndRef().getAbsoluteRow(this.getCurrentRow()));
         int endRow = Math.max(var21.getStartRef().getAbsoluteRow(this.getCurrentRow()), var21.getEndRef().getAbsoluteRow(this.getCurrentRow()));
         int i;
         int j;
         if(sum_range instanceof CellRef) {
            CellRef refWorksheet = (CellRef)sum_range;
            i = refWorksheet.getAbsoluteColumn(this.getCurrentColumn());
            j = refWorksheet.getAbsoluteRow(this.getCurrentRow());
            var18 = i;
            var20 = j;
         } else if(sum_range instanceof CellRange) {
            CellRange var26 = (CellRange)sum_range;
            i = Math.min(var26.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), var26.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
            j = Math.min(var26.getStartRef().getAbsoluteRow(this.getCurrentRow()), var26.getEndRef().getAbsoluteRow(this.getCurrentRow()));
            var18 = i;
            var20 = j;
         }

         Worksheet var25 = var21.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());

         for(i = 0; i <= endRow - startRow; ++i) {
            for(j = 0; j <= var24 - var23; ++j) {
               Cell curCell = (Cell)var25.get(i + startRow, j + var23);
               if(this.countif(curCell, criteria) == 1) {
                  Cell sumCell = (Cell)var25.get(i + var20, j + var18);
                  result += this.cal(sumCell);
               }
            }
         }
      }

      return result;
   }

   private double cal(Cell cell) {
      double result = 0.0D;
      if(this.isEmptyCell(cell)) {
         return result;
      } else {
         if(this.mEvaluator.isNumber(cell.getStringValue())) {
            result = Double.parseDouble(cell.getStringValue());
         }

         return result;
      }
   }

   private int countif(CellRef cellRef, Object objCriteria) {
      boolean result = false;
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      int result1 = this.countif(cell, objCriteria);
      return result1;
   }

   private int countif(Cell cell, Object objCriteria) {
      byte result = 0;
      String criteria = String.valueOf(objCriteria);
      if(!this.isEmptyCell(cell)) {
         String value = cell.getStringValue();
         if(this.mEvaluator.isEqualExp(criteria)) {
            if(!this.mEvaluator.isDateTime(value) && !this.mEvaluator.isNumber(value) && !this.mEvaluator.isBoolean(value)) {
               result = 1;
            }
         } else if(criteria.equals("<>*")) {
            if(this.mEvaluator.isDateTime(value) || this.mEvaluator.isNumber(value) || this.mEvaluator.isBoolean(value)) {
               result = 1;
            }
         } else if(this.mEvaluator.isExp(criteria)) {
            if(this.mEvaluator.evaluate(criteria, value)) {
               result = 1;
            }
         } else if(this.mEvaluator.evaluateWildCard(criteria, value)) {
            result = 1;
         }
      } else if(criteria.isEmpty()) {
         result = 1;
      } else if(this.mEvaluator.isNotEqualExp(criteria)) {
         result = 1;
      } else if(cell != null && criteria.equals(cell.getStringValue())) {
         result = 1;
      }

      return result;
   }

   private boolean isEmptyCell(Cell cell) {
      return cell == null || cell.isEmpty();
   }
}
