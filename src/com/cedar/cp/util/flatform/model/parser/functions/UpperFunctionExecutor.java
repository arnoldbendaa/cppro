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

public class UpperFunctionExecutor extends FunctionExecutor {

   public UpperFunctionExecutor() {
      this.setName("UPPER");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function UPPER(). Expected 1 got 0");
      } else if(params.size() != 1) {
         throw new IllegalStateException("Wrong number of parameters to function UPPER(). Expected 1 got " + params.size());
      } else {
         Object param = params.get(0);
         if(param instanceof CellErrorValue) {
            return param;
         } else if(param instanceof CellRef) {
            if(((CellRef)param).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
               return CellErrorValue.NAME;
            } else {
               Worksheet refWorksheet = ((CellRef)param).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
               Cell cell = ((CellRef)param).getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
               return cell == null?CellErrorValue.NAME:this.upper((CellRef)param);
            }
         } else {
            return param instanceof String?this.upper(param.toString()):(param instanceof Number?param:CellErrorValue.VALUE);
         }
      }
   }

   private String upper(CellRef cellRef) {
      String result = "";
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      if(cell != null) {
         result = cell.getStringValue().toUpperCase();
      }

      return result;
   }

   private String upper(String value) {
      return value.toUpperCase();
   }
}
