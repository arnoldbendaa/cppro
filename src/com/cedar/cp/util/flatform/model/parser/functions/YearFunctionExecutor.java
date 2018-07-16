// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.DateUtils;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YearFunctionExecutor extends FunctionExecutor {

   private static SimpleDateFormat mDateFormat = new SimpleDateFormat();


   public YearFunctionExecutor() {
      this.setName("YEAR");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function YEAR(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function YEAR(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         if(param instanceof CellRef) {
            CellRef cellRef = (CellRef)param;
            param = this.mFormulaExecutor.getNumericCellValue(cellRef);
            if(param instanceof CellErrorValue) {
               return param;
            }

            if(param instanceof Date) {
               Calendar c = Calendar.getInstance();
               c.setTime((Date)param);
               return Integer.valueOf(c.get(1));
            }
         }

         return param instanceof String?this.year(param.toString()):(param instanceof Number?this.year(((Number)param).intValue()):CellErrorValue.VALUE);
      }
   }

   private Object year(String value) {
      try {
         int e = Integer.parseInt(value);
         return this.year(e);
      } catch (NumberFormatException var5) {
         Date date = DateUtils.parseDateTime(mDateFormat, value);
         if(date == null) {
            return CellErrorValue.VALUE;
         } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return Integer.valueOf(c.get(1));
         }
      }
   }

   private Object year(int value) {
      if(value == 0) {
         return Integer.valueOf(1900);
      } else {
         Calendar c;
         if(value >= 1 && value <= 60) {
            c = Calendar.getInstance();
            c.set(1900, 0, 1);
            c.add(6, value - 1);
            return Integer.valueOf(c.get(1));
         } else {
            c = Calendar.getInstance();
            c.set(1900, 0, 1);
            c.add(6, value - 2);
            return Integer.valueOf(c.get(1));
         }
      }
   }

}
