// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
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

public class DayFunctionExecutor extends FunctionExecutor {

   private static SimpleDateFormat mDateFormat = new SimpleDateFormat();


   public DayFunctionExecutor() {
      this.setName("DAY");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function DAY(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function DAY(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         if(param instanceof CellRef) {
            CellRef cellRef = (CellRef)param;
            param = this.getFormulaExecutor().getCellValue(cellRef);
            if(param == CellErrorValue.VALUE) {
               return Integer.valueOf(0);
            }

            if(param instanceof CellErrorValue) {
               return param;
            }

            if(param instanceof Date) {
               Calendar c = Calendar.getInstance();
               c.setTime((Date)param);
               return Integer.valueOf(c.get(5));
            }
         }

         return param == CellErrorValue.VALUE?Integer.valueOf(0):(param instanceof String?(param.toString().trim().length() == 0?Integer.valueOf(0):this.day(param.toString())):(param instanceof Number?this.day(((Number)param).intValue()):CellErrorValue.VALUE));
      }
   }

   private Object day(String value) {
      try {
         int e = Integer.parseInt(value);
         return this.day(e);
      } catch (NumberFormatException var5) {
         Date date = DateUtils.parseDateTime(mDateFormat, value);
         if(date == null) {
            return CellErrorValue.VALUE;
         } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return Integer.valueOf(c.get(5));
         }
      }
   }

   private Object day(int value) {
      if(value == 0) {
         return Integer.valueOf(0);
      } else {
         Calendar c;
         if(value >= 1 && value <= 59) {
            c = Calendar.getInstance();
            c.set(1900, 0, 1);
            c.add(6, value - 1);
            return Integer.valueOf(c.get(5));
         } else if(value == 60) {
            return Integer.valueOf(29);
         } else {
            c = Calendar.getInstance();
            c.set(1900, 0, 1);
            c.add(6, value - 2);
            return Integer.valueOf(c.get(5));
         }
      }
   }

}
