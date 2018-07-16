// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.List;

public class SignFunctionExecutor extends FunctionExecutor {

   public SignFunctionExecutor() {
      this.setName("SIGN");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function SIGN(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function SIGN(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         if(param instanceof CellRef) {
            param = this.mFormulaExecutor.getNumericCellValue((CellRef)param);
         }

         return param instanceof Number?Double.valueOf(this.sign(((Number)param).doubleValue())):(param instanceof CellErrorValue?param:(param instanceof String && CellErrorValue.isErrorString(String.valueOf(param))?CellErrorValue.getError(String.valueOf(param)):CellErrorValue.VALUE));
      }
   }

   private double sign(double value) {
      return Math.signum(value);
   }
}
