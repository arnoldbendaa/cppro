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

public class TruncFunctionExecutor extends FunctionExecutor {

   public TruncFunctionExecutor() {
      this.setName("TRUNC");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function TRUNC(). Expected 1 or 2");
      } else if(params.size() != 1 && params.size() != 2) {
         throw new IllegalStateException("Wrong number of parameters to function TRUNC(). Expected Expected 1 or 2");
      } else {
         Object param1 = params.get(0);
         if(param1 instanceof CellRef) {
            param1 = this.mFormulaExecutor.getNumericCellValue((CellRef)param1);
         }

         if(param1 instanceof String) {
            try {
               if(((String)param1).isEmpty()) {
                  return CellErrorValue.VALUE;
               }

               param1 = Double.valueOf(Double.parseDouble((String)param1));
            } catch (NumberFormatException var7) {
               return CellErrorValue.VALUE;
            }
         }

         if(param1 instanceof CellErrorValue) {
            return param1;
         } else if(param1 instanceof String && CellErrorValue.isErrorString(String.valueOf(param1))) {
            return CellErrorValue.getError(String.valueOf(param1));
         } else {
            int numDigit = 0;
            if(params.size() == 2) {
               Object param2 = params.get(1);
               if(param2 instanceof CellRef) {
                  param2 = this.mFormulaExecutor.getNumericCellValue((CellRef)param2);
               }

               if(param2 == null) {
                  return CellErrorValue.VALUE;
               }

               if(param2 instanceof CellErrorValue) {
                  return param2;
               }

               if(param2 instanceof String && CellErrorValue.isErrorString(String.valueOf(param2))) {
                  return CellErrorValue.getError(String.valueOf(param2));
               }

               if(param2 instanceof Number) {
                  numDigit = ((Number)param2).intValue();
               }
            }

            return param1 instanceof Number?Double.valueOf(this.trunc(((Number)param1).doubleValue(), numDigit)):CellErrorValue.VALUE;
         }
      }
   }

   private double trunc(double number, int numDigit) {
      String[] s = String.valueOf(number).split(".");
      int noOfDecimal = 0;
      if(s.length == 2) {
         noOfDecimal = s[1].length();
      }

      if(numDigit > noOfDecimal) {
         return number;
      } else {
         BigDecimal bd = new BigDecimal(number);
         bd = bd.setScale(numDigit, 1);
         return bd.doubleValue();
      }
   }
}
