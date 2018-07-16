// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.List;

public class IntFunctionExecutor extends FunctionExecutor {

   public IntFunctionExecutor() {
      this.setName("INT");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function INT(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function INT(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         if(param instanceof CellRef) {
            param = this.mFormulaExecutor.getNumericCellValue((CellRef)param);
         }

         return param instanceof Number?Double.valueOf(this.intF(((Number)param).doubleValue())):(param instanceof CellErrorValue?param:(param instanceof String && CellErrorValue.isErrorString(String.valueOf(param))?CellErrorValue.getError(String.valueOf(param)):CellErrorValue.VALUE));
      }
   }

   private double intF(double value) {
      return Math.floor(value);
   }
}
