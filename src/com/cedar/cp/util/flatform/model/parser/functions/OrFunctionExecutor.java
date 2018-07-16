// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.Iterator;
import java.util.List;

public class OrFunctionExecutor extends FunctionExecutor {

   public OrFunctionExecutor() {
      this.setName("OR");
   }

   public Object execute(List params) {
      Iterator i$ = params.iterator();

      Object param;
      do {
         if(!i$.hasNext()) {
            return Boolean.valueOf(false);
         }

         param = i$.next();
         param = this.getBooleanValue(param);
      } while(!(param instanceof Boolean) || !((Boolean)param).booleanValue());

      return Boolean.valueOf(true);
   }

   public void preProcessParameter(Object parameter) {
      Object booleanValue = this.getBooleanValue(parameter);
      if(booleanValue instanceof Boolean && ((Boolean)booleanValue).booleanValue()) {
         this.getFormulaExecutor().setSematicProcessingEnabled(false);
      }

   }
}
