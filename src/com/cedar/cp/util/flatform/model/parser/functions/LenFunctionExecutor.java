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
import java.util.regex.Pattern;

public class LenFunctionExecutor extends FunctionExecutor {

   public LenFunctionExecutor() {
      this.setName("LEN");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function LEN(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function LEN(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         return param instanceof CellRef?(((CellRef)param).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null?CellErrorValue.REF:Integer.valueOf(this.len((CellRef)param))):(param instanceof String?Integer.valueOf(this.len(param.toString())):(param instanceof Number?(Pattern.matches("[0-9]*.0", param.toString())?Integer.valueOf(this.len(param.toString()) - 2):Integer.valueOf(this.len(param.toString()))):CellErrorValue.VALUE));
      }
   }

   private int len(CellRef cellRef) {
      int result = 0;
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      if(cell != null) {
         result = cell.getStringValue().length();
      }

      return result;
   }

   private int len(String value) {
      return value.length();
   }
}
