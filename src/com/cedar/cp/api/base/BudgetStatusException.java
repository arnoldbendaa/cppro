// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ChangeBudgetStateResult;

public class BudgetStatusException extends ValidationException {

   private ChangeBudgetStateResult mResult;


   public BudgetStatusException(ChangeBudgetStateResult reason) {
      super(reason.toString());
      this.mResult = reason;
   }

   public ChangeBudgetStateResult getResult() {
      return this.mResult;
   }

   public void setResult(ChangeBudgetStateResult result) {
      this.mResult = result;
   }
}
