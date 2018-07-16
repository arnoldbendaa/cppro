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

public class CountBlankFunctionExecutor extends FunctionExecutor {

   public CountBlankFunctionExecutor() {
      this.setName("COUNTBLANK");
   }

   public Object execute(List params) {
      double value = 0.0D;
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function COUNTBLANK()");
      } else {
         Iterator i$ = params.iterator();

         while(i$.hasNext()) {
            Object param = i$.next();
            if(param instanceof CellRef) {
               if(((CellRef)param).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               value += (double)this.count((CellRef)param);
            } else if(param instanceof CellRange) {
               if(((CellRange)param).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               value += (double)this.count((CellRange)param);
            } else if(param instanceof Number) {
               value += ((Number)param).doubleValue();
            } else if(!(param instanceof CellErrorValue)) {
               if(!(param instanceof String)) {
                  throw new IllegalArgumentException("Unexpected parameter " + param + " in COUNTBLANK() function");
               }

               if(!CellErrorValue.isErrorString((String)param)) {
                  throw new IllegalArgumentException("Unexpected String type parameter COUNTBLANK() function");
               }
            }
         }

         return Double.valueOf(value);
      }
   }

   private int count(CellRef cellRef) {
      Cell cell = (Cell)this.mFormulaExecutor.getWorksheetCell(cellRef.getWorksheet(this.mFormulaExecutor.getWorksheet()), cellRef);
      if(cell != null) {
         String cellText = cell.getCellText();
         if(cellText == null || cellText.trim().length() == 0) {
            return 1;
         }
      }

      return 0;
   }

   private int count(CellRange cellRange) {
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int result = 0;
      Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, endRow, endColumn);

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink cellLink = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell cell = (Cell)refWorksheet.get(cellLink.getRow(), cellLink.getColumn());
         if(cell != null) {
            String cellText = cell.getCellText();
            if(cellText == null || cellText.trim().length() == 0) {
               ++result;
            }
         }
      }

      return result;
   }
}
