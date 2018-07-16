// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.flatform.model.parser.ValueUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Days360FunctionExecutor extends FunctionExecutor {

   public Days360FunctionExecutor() {
      this.setName("DAYS360");
   }

   public Object execute(List params) {
      if(params != null && (params.size() == 2 || params.size() == 3)) {
         Object param1 = params.get(0);
         Date startDate = null;
         if(param1 instanceof CellRef) {
            startDate = this.convert2Date((CellRef)param1);
         } else if(param1 instanceof Number) {
            startDate = ValueUtils.convert2Date(((Number)param1).intValue());
         } else {
            if(!(param1 instanceof String)) {
               return CellErrorValue.VALUE;
            }

            startDate = ValueUtils.convert2Date((String)param1, false);
            if(startDate == null) {
               return CellErrorValue.VALUE;
            }
         }

         Object param2 = params.get(1);
         Date endDate = null;
         if(param2 instanceof CellRef) {
            endDate = this.convert2Date((CellRef)param2);
         } else if(param2 instanceof Number) {
            endDate = ValueUtils.convert2Date(((Number)param2).intValue());
         } else {
            if(!(param2 instanceof String)) {
               return CellErrorValue.VALUE;
            }

            endDate = ValueUtils.convert2Date((String)param2, false);
            if(endDate == null) {
               return CellErrorValue.VALUE;
            }
         }

         boolean method = false;
         if(params.size() == 3) {
            Object param3 = params.get(2);
            if(param3 instanceof Boolean) {
               method = ((Boolean)param3).booleanValue();
            } else {
               if(!(param3 instanceof CellRef)) {
                  return CellErrorValue.VALUE;
               }

               param3 = this.getMethod((CellRef)param3);
               if(param3 == null) {
                  return CellErrorValue.VALUE;
               }

               method = ((Boolean)param3).booleanValue();
            }
         }

         return Integer.valueOf(this.days360(startDate, endDate, method));
      } else {
         throw new IllegalStateException("Wrong number of parameters to function DAYS360()");
      }
   }

   private int days360(Date startDate, Date endDate, boolean method) {
      boolean numOfStartDate = false;
      boolean numOfEndDate = false;
      Calendar c = Calendar.getInstance();
      c.set(1900, 0, 1);
      int dayOfStart = -1;
      Calendar cEnd;
      int var9;
      if(startDate != null) {
         cEnd = Calendar.getInstance();
         cEnd.setTime(startDate);
         dayOfStart = cEnd.get(5);
         if(dayOfStart == 31) {
            cEnd.add(6, -1);
         }

         var9 = cEnd.get(1) * 360 + (cEnd.get(2) + 1) * 30 + cEnd.get(5);
         if(cEnd.get(1) == c.get(1) && cEnd.get(2) == c.get(2) && cEnd.get(5) == c.get(5)) {
            --var9;
         }
      } else {
         var9 = 684030;
      }

      int var10;
      if(endDate != null) {
         cEnd = Calendar.getInstance();
         cEnd.setTime(endDate);
         if(method) {
            if(cEnd.get(5) == 31) {
               cEnd.add(6, -1);
            }

            var10 = cEnd.get(1) * 360 + (cEnd.get(2) + 1) * 30 + cEnd.get(5);
            if(cEnd.get(1) == c.get(1) && cEnd.get(2) == c.get(2) && cEnd.get(5) == c.get(5)) {
               --var10;
            }
         } else if(cEnd.get(1) == c.get(1) && cEnd.get(2) == c.get(2) && cEnd.get(5) == c.get(5)) {
            var10 = c.get(1) * 360 + (c.get(2) + 1) * 30 + c.get(5) - 1;
         } else {
            if(dayOfStart != -1 && cEnd.get(5) == 31) {
               if(dayOfStart < 30) {
                  cEnd.add(6, 1);
               } else {
                  cEnd.add(6, -1);
               }
            }

            var10 = cEnd.get(1) * 360 + (cEnd.get(2) + 1) * 30 + cEnd.get(5);
         }
      } else {
         var10 = 684030;
      }

      return var10 - var9;
   }

   private Object getMethod(CellRef cellRef) {
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      Boolean result = Boolean.valueOf(false);
      if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
         if(cell.getStringValue().toUpperCase().equals("TRUE")) {
            result = Boolean.valueOf(true);
         } else {
            if(!cell.getStringValue().toUpperCase().equals("FALSE")) {
               return null;
            }

            result = Boolean.valueOf(false);
         }
      }

      return result;
   }

   private Date convert2Date(CellRef cellRef) {
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      Date result = null;
      if(cell != null && cell.getStringValue() != null && !cell.getStringValue().equals("")) {
         result = ValueUtils.convert2Date(cell.getStringValue(), false);
      }

      return result;
   }
}
