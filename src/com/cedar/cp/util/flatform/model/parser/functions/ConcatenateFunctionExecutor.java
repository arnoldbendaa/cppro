// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.Iterator;
import java.util.List;

public class ConcatenateFunctionExecutor extends FunctionExecutor {

   public ConcatenateFunctionExecutor() {
      this.setName("CONCATENATE");
   }

   public Object execute(List params) {
      StringBuilder sb = new StringBuilder();
      Iterator i$ = params.iterator();

      while(i$.hasNext()) {
         Object param = i$.next();
         if(param instanceof CellRef) {
            param = this.mFormulaExecutor.getCellValue((CellRef)param);
         }

         if(!(param instanceof CellErrorValue)) {
            if(param instanceof String && CellErrorValue.isErrorString(String.valueOf(param))) {
               return CellErrorValue.getError(String.valueOf(param));
            }

            if(param != null) {
               sb.append(String.valueOf(param));
            }
         }
      }

      return sb.toString();
   }
}
