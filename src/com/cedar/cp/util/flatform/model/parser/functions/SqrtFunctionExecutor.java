// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.List;

public class SqrtFunctionExecutor extends FunctionExecutor {

   public SqrtFunctionExecutor() {
      this.setName("SQRT");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function SQRT(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function SQRT(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         if(param instanceof CellRef) {
            param = this.mFormulaExecutor.getNumericCellValue((CellRef)param);
         }

         if(param instanceof Number) {
            double num = ((Number)param).doubleValue();
            return num < 0.0D?CellErrorValue.NUM:Double.valueOf(this.sqrt(((Number)param).doubleValue()));
         } else {
            return param instanceof CellErrorValue?param:(param instanceof String && CellErrorValue.isErrorString(String.valueOf(param))?CellErrorValue.getError(String.valueOf(param)):CellErrorValue.VALUE);
         }
      }
   }

   private double sqrt(double value) {
      return Math.sqrt(value);
   }
}
