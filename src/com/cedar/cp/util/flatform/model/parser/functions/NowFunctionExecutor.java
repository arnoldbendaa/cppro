// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.Date;
import java.util.List;

public class NowFunctionExecutor extends FunctionExecutor {

   public NowFunctionExecutor() {
      this.setName("NOW");
   }

   public Object execute(List params) {
      return new Date();
   }
}
