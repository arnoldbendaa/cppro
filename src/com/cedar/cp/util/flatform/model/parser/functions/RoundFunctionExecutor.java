// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class RoundFunctionExecutor extends FunctionExecutor {

   public RoundFunctionExecutor() {
      this.setName("ROUND");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function ROUND(). Expected 2 got 0");
      } else if(params.size() != 2) {
         throw new IllegalStateException("Wrong number of parameters to function ROUND(). Expected 2 got " + params.size());
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

            return param2 instanceof CellErrorValue?param2:(param2 instanceof String && CellErrorValue.isErrorString(String.valueOf(param2))?CellErrorValue.getError(String.valueOf(param2)):(param1 instanceof Number && param2 instanceof Number?Double.valueOf(this.round(((Number)param1).doubleValue(), ((Number)param2).intValue())):CellErrorValue.VALUE));
         }
      }
   }

   private double round(double base, int exponent) {
      String value = "";
      if(Math.abs(base) >= 1.0E7D) {
         DecimalFormat dotPos = new DecimalFormat();
         dotPos.setGroupingUsed(false);
         dotPos.setMaximumFractionDigits(4);
         value = dotPos.format(base);
      } else {
         value = String.valueOf(base);
      }

      int dotPos1 = value.indexOf(".");
      String s = "";
      if(exponent < 0) {
         if(dotPos1 < 0) {
            dotPos1 = value.length();
         }

         if(dotPos1 + exponent < 0) {
            return 0.0D;
         }

         s = value.substring(dotPos1 + exponent, dotPos1 + exponent + 1);
      } else {
         if(dotPos1 < 0 || exponent > dotPos1 + exponent + 1 || dotPos1 + exponent + 1 >= value.length()) {
            return base;
         }

         s = value.substring(dotPos1 + exponent + 1, dotPos1 + exponent + 2);
      }

      BigDecimal bd = new BigDecimal(base);
      if(Integer.valueOf(s).intValue() >= 5) {
         bd = bd.setScale(exponent, 0);
      } else {
         bd = bd.setScale(exponent, 1);
      }

      return bd.doubleValue();
   }
}
