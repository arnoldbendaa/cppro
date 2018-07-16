// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.List;

public class PowerFunctionExecutor extends FunctionExecutor {

   public PowerFunctionExecutor() {
      this.setName("POWER");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function POWER(). Expected 2 got 0");
      } else if(params.size() != 2) {
         throw new IllegalStateException("Wrong number of parameters to function POWER(). Expected 2 got " + params.size());
      } else {
         Object param1 = params.get(0);
         boolean baseEmpty = false;
         if(param1 instanceof CellRef) {
            Worksheet param2 = ((CellRef)param1).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
            Cell expEmpty = ((CellRef)param1).getCell(param2, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
            if(expEmpty == null || expEmpty != null && (expEmpty.getStringValue() == null || expEmpty.getStringValue().equals(""))) {
               baseEmpty = true;
            }

            param1 = this.mFormulaExecutor.getNumericCellValue((CellRef)param1);
         }

         if(param1 instanceof CellErrorValue) {
            return param1;
         } else if(param1 instanceof String && CellErrorValue.isErrorString(String.valueOf(param1))) {
            return CellErrorValue.getError(String.valueOf(param1));
         } else {
            Object param21 = params.get(1);
            boolean expEmpty1 = false;
            if(param21 instanceof CellRef) {
               Worksheet refWorksheet = ((CellRef)param21).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cell = ((CellRef)param21).getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
               if(cell == null || cell != null && (cell.getStringValue() == null || cell.getStringValue().equals(""))) {
                  expEmpty1 = true;
               }

               param21 = this.mFormulaExecutor.getNumericCellValue((CellRef)param21);
            }

            return param21 instanceof CellErrorValue?param21:(param21 instanceof String && CellErrorValue.isErrorString(String.valueOf(param21))?CellErrorValue.getError(String.valueOf(param21)):(baseEmpty && expEmpty1?CellErrorValue.NUM:(param1 instanceof Number && param21 instanceof Number?Double.valueOf(this.power(((Number)param1).doubleValue(), ((Number)param21).doubleValue())):CellErrorValue.VALUE)));
         }
      }
   }

   private double power(double base, double exponent) {
      return Math.pow(base, exponent);
   }
}
