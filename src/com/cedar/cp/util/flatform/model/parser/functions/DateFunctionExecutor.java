// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.Calendar;
import java.util.List;

public class DateFunctionExecutor extends FunctionExecutor {

   public DateFunctionExecutor() {
      this.setName("DATE");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function DATE(). Expected 3 got 0");
      } else if(params.size() != 3) {
         throw new IllegalStateException("Wrong number of parameters to function DATE(). Expected 3 got " + params.size());
      } else {
         Object yearParam = this.getIntegerValue(params.get(0));
         if(yearParam instanceof CellErrorValue) {
            return yearParam;
         } else if(yearParam == null) {
            return CellErrorValue.NUM;
         } else {
            Object monthParam = this.getIntegerValue(params.get(1));
            if(monthParam instanceof CellErrorValue) {
               return yearParam;
            } else if(monthParam == null) {
               return CellErrorValue.NUM;
            } else {
               Object dayParam = this.getIntegerValue(params.get(2));
               if(dayParam instanceof CellErrorValue) {
                  return dayParam;
               } else if(dayParam == null) {
                  return CellErrorValue.NUM;
               } else {
                  Calendar c = Calendar.getInstance();
                  c.set(1, ((Number)yearParam).intValue());
                  c.set(2, ((Number)monthParam).intValue() - 1);
                  c.set(5, ((Number)dayParam).intValue());
                  return c.getTime();
               }
            }
         }
      }
   }
}
