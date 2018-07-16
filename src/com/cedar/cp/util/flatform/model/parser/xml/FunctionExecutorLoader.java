// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.xml;

import com.cedar.cp.util.flatform.model.parser.FormulaExecutor;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionDetail;

public class FunctionExecutorLoader {

   private FlatformFunctionDetail functionDetail = null;
   private FunctionExecutor executor = null;
   private FormulaExecutor formulaExecutor = null;


   public FunctionExecutorLoader(FlatformFunctionDetail flatformFunctionDetail, FormulaExecutor formulaExecutor) {
      this.functionDetail = flatformFunctionDetail;
      this.formulaExecutor = formulaExecutor;
   }

   public FlatformFunctionDetail getFunctionDetail() {
      return this.functionDetail;
   }

   public void setFunctionDetail(FlatformFunctionDetail functionDetail) {
      this.functionDetail = functionDetail;
   }

   public FunctionExecutor getExecutor() {
      return this.executor == null?this.load():this.executor;
   }

   private FunctionExecutor load() {
      try {
         Class e = Class.forName(this.functionDetail.getFunctionClass());
         FunctionExecutor fe = (FunctionExecutor)e.newInstance();
         fe.setFormulaExecutor(this.formulaExecutor);
         return fe;
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException("Failed to instance spreadsheet function class:" + this.functionDetail.getClass());
      }
   }
}
