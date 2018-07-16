// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.math.BigDecimal;
import java.util.List;

public class RounddownFunctionExecutor extends FunctionExecutor {

   public RounddownFunctionExecutor() {
      this.setName("ROUNDDOWN");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function ROUNDDOWN(). Expected 2 got 0");
      } else if(params.size() != 2) {
         throw new IllegalStateException("Wrong number of parameters to function ROUNDDOWN(). Expected 2 got " + params.size());
      } else {
         Object param1 = params.get(0);
         if(param1 instanceof CellRef) {
            param1 = this.mFormulaExecutor.getNumericCellValue((CellRef)param1);
         }

         if(param1 instanceof CellErrorValue) {
            return param1;
         } else if(param1 instanceof String && CellErrorValue.isErrorString(String.valueOf(param1))) {
            return CellErrorValue.getError(String.valueOf(param1));
         } else {
            Object param2 = params.get(1);
            if(param2 instanceof CellRef) {
               param2 = this.mFormulaExecutor.getNumericCellValue((CellRef)param2);
            }

            return param2 instanceof CellErrorValue?param2:(param2 instanceof String && CellErrorValue.isErrorString(String.valueOf(param2))?CellErrorValue.getError(String.valueOf(param2)):(param1 instanceof Number && param2 instanceof Number?Double.valueOf(this.roundup(((Number)param1).doubleValue(), ((Number)param2).intValue())):CellErrorValue.VALUE));
         }
      }
   }

   private double roundup(double base, int exponent) {
      BigDecimal bd = new BigDecimal(base);
      bd = bd.setScale(exponent, 1);
      return bd.doubleValue();
   }
}
