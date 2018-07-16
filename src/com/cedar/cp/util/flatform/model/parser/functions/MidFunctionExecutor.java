// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.List;

public class MidFunctionExecutor extends FunctionExecutor {

   public MidFunctionExecutor() {
      this.setName("MID");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function MID(). Expected 3 got 0");
      } else if(params.size() != 3) {
         throw new IllegalStateException("Wrong number of parameters to function MID(). Expected 3 got " + params.size());
      } else {
         Object text = params.get(0);
         if(text instanceof CellRef) {
            if(((CellRef)text).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
               return CellErrorValue.REF;
            }

            text = this.getText((CellRef)text);
         } else if(text instanceof String) {
            text = text.toString();
         } else {
            if(!(text instanceof Number)) {
               return CellErrorValue.VALUE;
            }

            text = Double.valueOf(((Number)text).doubleValue());
         }

         Object startNum = params.get(1);
         if(startNum instanceof CellRef) {
            startNum = this.mFormulaExecutor.getNumericCellValue((CellRef)startNum);
         } else if(startNum instanceof Number) {
            startNum = Double.valueOf(((Number)startNum).doubleValue());
         } else {
            if(!(startNum instanceof String)) {
               return CellErrorValue.VALUE;
            }

            try {
               startNum = Double.valueOf(Double.parseDouble((String)startNum));
            } catch (NumberFormatException var7) {
               return CellErrorValue.VALUE;
            }
         }

         Object numChars = params.get(2);
         if(numChars instanceof CellRef) {
            numChars = this.mFormulaExecutor.getNumericCellValue((CellRef)numChars);
         } else if(numChars instanceof Number) {
            numChars = Double.valueOf(((Number)numChars).doubleValue());
         } else {
            if(!(numChars instanceof String)) {
               return CellErrorValue.VALUE;
            }

            try {
               numChars = Double.valueOf(Double.parseDouble((String)numChars));
            } catch (NumberFormatException var6) {
               return CellErrorValue.VALUE;
            }
         }

         return this.mid(String.valueOf(text), ((Number)startNum).intValue(), ((Number)numChars).intValue());
      }
   }

   private String getText(CellRef cellRef) {
      String result = "";
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      if(cell != null) {
         result = cell.getStringValue();
      }

      return result;
   }

   private Object mid(String value, int startNum, int numChars) {
      return startNum >= 1 && numChars >= 0?(startNum > value.length()?"":(startNum + numChars > value.length()?value.substring(startNum - 1):value.substring(startNum - 1, numChars + startNum - 1))):CellErrorValue.VALUE;
   }
}
