// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.DateUtils;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRange;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class CountFunctionExecutor extends FunctionExecutor {

   private static SimpleDateFormat mDateFormat = new SimpleDateFormat();


   public CountFunctionExecutor() {
      this.setName("COUNT");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function COUNT()");
      } else if(params.size() > 30) {
         throw new IllegalStateException("Too many arguments to function COUNT(). Maximum of 30 arguments");
      } else {
         int cnt = 0;
         mDateFormat.setLenient(false);
         Iterator i$ = params.iterator();

         while(i$.hasNext()) {
            Object param = i$.next();
            if(param instanceof CellRef) {
               if(((CellRef)param).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               cnt += this.count((CellRef)param);
            } else if(param instanceof CellRange) {
               if(((CellRange)param).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               cnt += this.count((CellRange)param);
            } else if(param instanceof Number) {
               ++cnt;
            } else if(!(param instanceof CellErrorValue)) {
               if(!(param instanceof String)) {
                  throw new IllegalArgumentException("Unexpected parameter " + param + " in COUNT() function");
               }

               cnt += this.count((String)param);
            }
         }

         return Integer.valueOf(cnt);
      }
   }

   private int count(String value) {
      int result = 0;
      if(DateUtils.parseDateTime(mDateFormat, value) != null) {
         ++result;
      } else if(DateUtils.parseTime(mDateFormat, value) != null) {
         ++result;
      } else {
         try {
            Double.parseDouble(value);
            ++result;
         } catch (NumberFormatException var4) {
            ;
         }
      }

      return result;
   }

   private int count(CellRef cellRef) {
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      int result = 0;
      if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
         result += this.count(cell.getStringValue());
      }

      return result;
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
         if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
            result += this.count(cell.getStringValue());
         }
      }

      return result;
   }

}
