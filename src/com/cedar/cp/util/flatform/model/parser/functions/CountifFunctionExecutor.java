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
import com.cedar.cp.util.flatform.model.parser.ExpressionEvaluator;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.util.Iterator;
import java.util.List;

public class CountifFunctionExecutor extends FunctionExecutor {

   public CountifFunctionExecutor() {
      this.setName("COUNTIF");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function COUNTIF(). Expected 2 got 0");
      } else if(params.size() != 2) {
         throw new IllegalStateException("Wrong number of parameters to function COUNTIF(). Expected 2 got " + params.size());
      } else {
         Object param1 = params.get(0);
         if(!(param1 instanceof CellRef) && !(param1 instanceof CellRange)) {
            return CellErrorValue.NAME;
         } else {
            Object param2 = params.get(1);
            String param21;
            if(param2 instanceof CellRef) {
               Worksheet refWorksheet = ((CellRef)param2).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cell = ((CellRef)param2).getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
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

            return Integer.valueOf(this.countif(param1, param21));
         }
      }
   }

   private int countif(Object value, Object criteria) {
      int result = 0;
      if(value instanceof CellRef) {
         result = this.countif((CellRef)value, criteria);
      } else if(value instanceof CellRange) {
         result = this.countif((CellRange)value, criteria);
      }

      return result;
   }

   private int countif(CellRef cellRef, Object objCriteria) {
      boolean result = false;
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      int result1 = this.countif(cell, objCriteria);
      return result1;
   }

   private int countif(CellRange cellRange, Object objCriteria) {
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int result = 0;
      Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, endRow, endColumn);
      int numOfCell = this.countCell(startColumn, endColumn, startRow, endRow);

      int index;
      for(index = 0; cellIter.hasNext(); ++index) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)refWorksheet.get(cellLink.getRow(), cellLink.getColumn());
         result += this.countif(cell, objCriteria);
      }

      while(index < numOfCell) {
         result += this.countif(new Cell(), objCriteria);
         ++index;
      }

      return result;
   }

   private int countif(Cell cell, Object objCriteria) {
      byte result = 0;
      String criteria = String.valueOf(objCriteria);
      ExpressionEvaluator evaluator = new ExpressionEvaluator();
      if(!this.isEmptyCell(cell)) {
         String value = cell.getStringValue();
         if(evaluator.isEqualExp(criteria)) {
            if(!evaluator.isDateTime(value) && !evaluator.isNumber(value) && !evaluator.isBoolean(value)) {
               result = 1;
            }
         } else if(criteria.equals("<>*")) {
            if(evaluator.isDateTime(value) || evaluator.isNumber(value) || evaluator.isBoolean(value)) {
               result = 1;
            }
         } else if(evaluator.isExp(criteria)) {
            if(evaluator.evaluate(criteria, value)) {
               result = 1;
            }
         } else if(evaluator.evaluateWildCard(criteria, value)) {
            result = 1;
         }
      } else if(criteria.isEmpty()) {
         result = 1;
      } else if(evaluator.isNotEqualExp(criteria)) {
         result = 1;
      } else if(cell != null && criteria.equals(cell.getStringValue())) {
         result = 1;
      }

      return result;
   }

   private boolean isEmptyCell(Cell cell) {
      return cell == null || cell.isEmpty();
   }

   private int countCell(int startColumn, int endColumn, int startRow, int endRow) {
      int numOfCell = 0;
      if(startColumn != endColumn && startRow != endRow) {
         numOfCell = (endRow - startRow + 1) * (endColumn - startColumn + 1);
      } else if(startColumn == endColumn) {
         numOfCell = endRow - startRow + 1;
      } else if(startRow == endRow) {
         numOfCell = endColumn - startColumn + 1;
      }

      return numOfCell;
   }
}
