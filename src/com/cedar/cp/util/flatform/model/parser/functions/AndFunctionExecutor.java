// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.Iterator;
import java.util.List;

public class AndFunctionExecutor extends FunctionExecutor {

   public AndFunctionExecutor() {
      this.setName("AND");
   }

   public Object execute(List params) {
      Iterator i$ = params.iterator();

      Object param;
      do {
         if(!i$.hasNext()) {
            return Boolean.valueOf(true);
         }

         param = i$.next();
         param = this.getBooleanValue(param);
      } while(!(param instanceof Boolean) || ((Boolean)param).booleanValue());

      return Boolean.valueOf(false);
   }
}
